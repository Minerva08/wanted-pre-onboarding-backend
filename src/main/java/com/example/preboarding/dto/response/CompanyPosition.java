package com.example.preboarding.dto.response;

import com.example.preboarding.common.CommonResponse;
import com.example.preboarding.domain.Company;
import com.example.preboarding.domain.CompanyRole;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
public class CompanyPosition extends CommonResponse {
    private List<CompanyRole> companyRoleList;
}
