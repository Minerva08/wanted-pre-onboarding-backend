package com.example.preboarding.dto.response;

import com.example.preboarding.common.CommonResponse;
import com.example.preboarding.domain.Company;
import com.example.preboarding.domain.CompanyRole;
import com.example.preboarding.dto.JobRoleInfoDTO;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
public class CompanyPosition extends CommonResponse {
    private List<JobRoleInfoDTO> companyRoleList;
}
