package com.example.preboarding.repository.role;

import com.example.preboarding.domain.Role;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


import static com.example.preboarding.domain.QRole.role;
import static com.example.preboarding.domain.QCompanyRole.companyRole;
import static com.example.preboarding.domain.QJobPosition.jobPosition;

@Repository
@AllArgsConstructor
public class RoleRepositoryImpl implements RoleRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Role> findAllByCompanyNum(Long companyNum) {
        return queryFactory.selectFrom(role)
                .join(role.companyRoleList, companyRole)
                .where(
                        companyRole.company.comNum.eq(companyNum)
                                .and(
                                        queryFactory.selectOne()
                                                .from(jobPosition)
                                                .where(jobPosition.companyRole.eq(companyRole))
                                                .notExists()
                                )
                )
                .distinct()
                .fetch();
    }
}
