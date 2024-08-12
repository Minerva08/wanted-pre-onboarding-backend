package com.example.preboarding.mapper;


import com.example.preboarding.domain.Role;
import com.example.preboarding.dto.request.RoleReq;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
