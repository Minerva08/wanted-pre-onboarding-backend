package com.example.preboarding.repository.companyRole;

import com.example.preboarding.domain.CompanyRole;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.preboarding.domain.QCompany.company;
import static com.example.preboarding.domain.QCompanyRole.companyRole;
import static com.example.preboarding.domain.QRole.role;


@Repository
@Transactional
@AllArgsConstructor
public class CompanyRoleRepositoryImpl implements CompanyRoleRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<CompanyRole> findByCompanyNumAndRoleNum(Long comNum, Long roleNum) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(companyRole.company.comNum.eq(comNum));

        if (roleNum != null) {
            builder.and(companyRole.role.roleNum.eq(roleNum));
        }

        return queryFactory.selectFrom(companyRole)
                .join(companyRole.company, company).fetchJoin()
                .join(companyRole.role, role).fetchJoin()
                .where(builder)
                .stream().toList();
    }


}
