package com.example.preboarding.mapper;


import com.example.preboarding.domain.CompanyRole;
import com.example.preboarding.domain.JobPosition;
import com.example.preboarding.domain.Role;
import com.example.preboarding.dto.request.JobPositionPostReq;
import com.example.preboarding.dto.request.RoleReq;
import com.example.preboarding.dto.response.JobPostInfoRes;
import com.example.preboarding.repository.companyRole.CompanyRoleRepository;
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

}
