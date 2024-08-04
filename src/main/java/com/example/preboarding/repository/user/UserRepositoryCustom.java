package com.example.preboarding.repository.user;

import com.example.preboarding.dto.ApplyInfoDTO;
import com.example.preboarding.dto.ApplyUserDTO;

public interface UserRepositoryCustom {
    Long updateApplyStatus(Long userNum, Boolean applyStatus);

    ApplyInfoDTO findUserWithApplyAndJobPositionByUserNum(Long userNum);


}
