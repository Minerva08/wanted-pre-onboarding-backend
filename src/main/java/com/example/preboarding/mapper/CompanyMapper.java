package com.example.preboarding.mapper;


import com.example.preboarding.domain.Company;
import com.example.preboarding.domain.CompanyRole;
import com.example.preboarding.domain.JobPosition;
import com.example.preboarding.dto.request.CompanyReq;
import com.example.preboarding.dto.request.JobPositionPostReq;
import com.example.preboarding.dto.response.JobPostInfoRes;
import com.example.preboarding.repository.companyRole.CompanyRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CompanyMapper {

    public Company toEntity(CompanyReq dto) {

        return Company.builder()
                .nation(dto.getNation())
                .region(dto.getRegion())
                .comName(dto.getComName())
                .comId(dto.getComId())
                .build();
    }

}
