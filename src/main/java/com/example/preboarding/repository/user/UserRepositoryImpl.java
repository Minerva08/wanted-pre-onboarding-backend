package com.example.preboarding.repository.user;

import com.example.preboarding.dto.ApplyInfoDTO;
import com.example.preboarding.dto.ApplyUserDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.preboarding.domain.QApply.apply;
import static com.example.preboarding.domain.QCompany.company;
import static com.example.preboarding.domain.QCompanyRole.companyRole;
import static com.example.preboarding.domain.QJobPosition.jobPosition;
import static com.example.preboarding.domain.QRole.role;
import static com.example.preboarding.domain.QUser.user;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public Long  updateApplyStatus(Long userNum, Boolean applyStatus) {
        long updatedRows = queryFactory.update(user)
                .set(user.applyStatus, applyStatus)
                .where(user.userNum.eq(userNum))
                .execute();

        // If no rows were updated, return null or throw an exception
        if (updatedRows == 0) {
            throw new RuntimeException("No rows updated");
        }

        // Return the userNum if the update was successful
        return userNum;
    }

    @Override
    public ApplyInfoDTO findUserWithApplyAndJobPositionByUserNum(Long userNum) {

        return queryFactory.select(Projections.constructor(ApplyInfoDTO.class,
                        apply.applyNum,
                        user.userNum,
                        user.userId,
                        user.userName,
                        apply.jobPosition.num,
                        jobPosition.postTitle,
                        jobPosition.contents,
                        jobPosition.skill,
                        companyRole.role.roleId,
                        role.roleName, // Assuming you need roleName from Role
                        company.comNum,
                        company.comName,
                        company.region,
                        company.nation,
                        jobPosition.reward,
                        apply.applyDate // Assuming appliedDate is available in Apply entity
                ))
                .from(user)
                .leftJoin(user.apply, apply)
                .leftJoin(apply.jobPosition, jobPosition)
                .leftJoin(jobPosition.companyRole, companyRole) // Join CompanyRole
                .innerJoin(companyRole.role, role) // Join Role from CompanyRole
                .leftJoin(jobPosition.company, company)
                .where(user.userNum.eq(userNum))
                .fetchOne(); // Fetch one result
    }

}
