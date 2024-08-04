package com.example.preboarding.repository.user;

import com.example.preboarding.domain.User;
import com.example.preboarding.dto.ApplyInfoDTO;
import com.example.preboarding.dto.ApplyUserDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Long updateApplyStatus(Long userNum, Boolean applyStatus);
    ApplyInfoDTO findUserWithApplyAndJobPositionByUserNum(Long userNum);


}
