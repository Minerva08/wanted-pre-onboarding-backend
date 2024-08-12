package com.example.preboarding.mapper;


import com.example.preboarding.domain.CompanyRole;
import com.example.preboarding.domain.JobPosition;
import com.example.preboarding.domain.Role;
import com.example.preboarding.dto.JobRoleInfoDTO;
import com.example.preboarding.dto.request.RoleReq;
import com.example.preboarding.dto.response.JobPostInfoRes;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class RoleMapper {

    public Role toEntity(RoleReq dto) {

        return Role.builder()
                .roleId(dto.getRoleId())
                .roleName(dto.getRoleName())
                .build();
    }

    public List<JobRoleInfoDTO> toDto(List<CompanyRole> companyRoleList) {
        List<JobRoleInfoDTO> list = new ArrayList<>();


        for (CompanyRole companyRole : companyRoleList) {
            list.add(JobRoleInfoDTO.builder()
                    .companyRoleNum(companyRole.getCompanyRoleNum())
                    .roleNum(companyRole.getRole().getRoleNum())
                    .roleId(companyRole.getRole().getRoleId())
                    .roleName(companyRole.getRole().getRoleName())
                    .build());

        }
        return list;
    }

}
