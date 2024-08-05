package com.example.preboarding.repository.jobPosition;

import com.example.preboarding.domain.*;
import com.example.preboarding.dto.JobPostSearchDTO;
import com.example.preboarding.exception.CustomException;
import com.example.preboarding.exception.EnumResponseMessage;
import com.example.preboarding.exception.HttpErrorCode;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.preboarding.domain.QCompany.company;
import static com.example.preboarding.domain.QCompanyRole.companyRole;
import static com.example.preboarding.domain.QJobPosition.jobPosition;
import static com.example.preboarding.domain.QRole.role;

@Repository
@Transactional
@AllArgsConstructor
public class JobPositionRepositoryImpl implements JobPositionRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<JobPosition> findCompanyOtherPosition(Long comNum, Long postNum){

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(company.comNum.eq(comNum));

        if (postNum != null) {
            builder.and(jobPosition.num.ne(postNum));
        }

        return jpaQueryFactory.selectDistinct(jobPosition)
                .from(jobPosition)
                .join(jobPosition.companyRole, companyRole).fetchJoin()
                .join(companyRole.company, company).fetchJoin()
                .join(companyRole.role, role).fetchJoin()
                .where(builder)
                .fetch();

    }
    @Override
    public Page<JobPosition> searchPosition(JobPostSearchDTO search) {

        // Create the base query
        JPAQuery<JobPosition> query = jpaQueryFactory.selectFrom(jobPosition)
                .join(jobPosition.company, company)
                .join(jobPosition.companyRole, companyRole)
                .where(
                        comNameCondition(search.getComName(), company.comName),
                        regionCondition(search.getRegion(), company.region),
                        nationCondition(search.getNation(), company.nation),
                        roleNumCondition(search.getRoleNumList(), companyRole.role.roleNum)
                );

        // Fetch results with pagination
        List<JobPosition> jobPositions = query
                .offset(search.getPageable().getOffset())
                .limit(search.getPageable().getPageSize())
                .fetch();

        // Count query for pagination
        Long count = jpaQueryFactory.select(jobPosition.count())
                .from(jobPosition)
                .join(jobPosition.company, company)
                .join(jobPosition.companyRole, companyRole)
                .where(
                        comNameCondition(search.getComName(), company.comName),
                        regionCondition(search.getRegion(), company.region),
                        nationCondition(search.getNation(), company.nation),
                        roleNumCondition(search.getRoleNumList(), companyRole.role.roleNum)
                )
                .fetchOne();

        return new PageImpl<>(jobPositions, search.getPageable(), count);
    }


    private BooleanExpression comNameCondition(String comName, StringPath comNamePath) {
        if (comName == null || comName.isEmpty()) {
            return null;
        }
        return comNamePath.containsIgnoreCase(comName);
    }

    private BooleanExpression regionCondition(String region, StringPath regionPath) {
        if (region == null || region.isEmpty()) {
            return null;
        }
        return regionPath.eq(region);
    }

    private BooleanExpression nationCondition(String nation, StringPath nationPath) {
        if (nation == null || nation.isEmpty()) {
            return null;
        }
        return nationPath.eq(nation);
    }

    private BooleanExpression roleNumCondition(List<Long> roleNums, NumberPath<Long> roleNumPath) {
        if (roleNums == null || roleNums.isEmpty()) {
            return null;
        }
        return roleNumPath.in(roleNums);
    }



}
