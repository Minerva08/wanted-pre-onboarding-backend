package com.example.preboarding.mapper;


import com.example.preboarding.domain.Company;
import com.example.preboarding.dto.request.CompanyReq;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
