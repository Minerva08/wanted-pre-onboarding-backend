package com.example.preboarding.repository.apply;

import com.example.preboarding.dto.ApplyUserDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.preboarding.domain.QApply.apply;
import static com.example.preboarding.domain.QUser.user;

@Repository
@AllArgsConstructor
public class ApplyRepositoryImpl implements ApplyRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<ApplyUserDTO> findByApplyNum(Long applyNum) {
        return queryFactory.select(Projections.constructor(ApplyUserDTO.class,
                        apply.applyNum,
                        user.userNum,
                        user.userId,
                        user.userName,
                        apply.jobPosition.num))
                .from(apply)
                .join(apply.user, user)
                .where(apply.jobPosition.num.eq(applyNum))
                .fetch();
    }

}
