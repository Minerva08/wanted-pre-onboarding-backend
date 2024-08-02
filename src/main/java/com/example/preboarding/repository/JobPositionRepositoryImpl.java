package com.example.preboarding.repository;

import com.example.preboarding.domain.JobPosition;
import com.example.preboarding.domain.QCompany;
import com.example.preboarding.domain.QCompanyRole;
import com.example.preboarding.domain.QJobPosition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@AllArgsConstructor
public class JobPositionRepositoryImpl implements JobPositionRepositoryCustom{
    @PersistenceContext
    private EntityManager em;

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public JobPosition findByIdDetail(Long jobPositionNum){
        return em.find(JobPosition.class,jobPositionNum);
    }

    @Override
    public List<JobPosition> findCompanyOtherPosition(Long comNum, Long postNum){
        return em.createQuery("select j from JobPosition as j inner join j.company as c where c.comNum = :comNum and j.num != :postNum",JobPosition.class)
                .setParameter("comNum",comNum)
                .setParameter("postNum",postNum)
                .getResultList();
    }
    @Override
    public Page<JobPosition> searchPosition(String comName,String region,String nation, List<Long> roleNums, Pageable pageable) {
        QJobPosition jobPosition = QJobPosition.jobPosition;
        QCompany company = QCompany.company;
        QCompanyRole companyRole = QCompanyRole.companyRole;

        // Create the base query
        JPAQuery<JobPosition> query = jpaQueryFactory.selectFrom(jobPosition)
                .join(jobPosition.company, company)
                .join(jobPosition.companyRole, companyRole)
                .where(
                        comNameCondition(comName, company.comName),
                        regionCondition(region, company.region),
                        nationCondition(nation, company.nation),
                        roleNumCondition(roleNums, companyRole.role.roleNum)
                );

        // Fetch results with pagination
        List<JobPosition> jobPositions = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Count query for pagination
        Long count = jpaQueryFactory.select(jobPosition.count())
                .from(jobPosition)
                .join(jobPosition.company, company)
                .join(jobPosition.companyRole, companyRole)
                .where(
                        comNameCondition(comName, company.comName),
                        regionCondition(region, company.region),
                        nationCondition(nation, company.nation),
                        roleNumCondition(roleNums, companyRole.role.roleNum)
                )
                .fetchOne();

        return new PageImpl<>(jobPositions, pageable, count);
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
