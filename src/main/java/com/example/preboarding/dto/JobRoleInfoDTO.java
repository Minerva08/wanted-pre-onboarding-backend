package com.example.preboarding.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JobRoleInfoDTO {
    private Long companyRoleNum;
    private Long roleNum;
    private String roleId;
    private String roleName;
}
