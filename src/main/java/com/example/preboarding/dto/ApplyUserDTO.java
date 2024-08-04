package com.example.preboarding.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApplyUserDTO {
    private Long applyNum;
    private String userId;
    private String userName;
    private Long jobPostNum;

    public ApplyUserDTO(Long applyNum, String userId, String userName, Long jobPostNum) {
        this.applyNum = applyNum;
        this.userId = userId;
        this.userName = userName;
        this.jobPostNum = jobPostNum;
    }
}
