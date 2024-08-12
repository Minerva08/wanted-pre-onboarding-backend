package com.example.preboarding.service;

import com.example.preboarding.domain.User;
import com.example.preboarding.dto.ApplyInfoDTO;
import com.example.preboarding.dto.request.ApplicationReq;
import com.example.preboarding.dto.response.ApplicationRes;

public interface UserService {
    ApplicationRes applyJob(ApplicationReq applyRequest);

    long cancelApplication(long applyNum);

    ApplyInfoDTO getApplication(Long userNum);

    User getUserInfo(Long userNum);
}
