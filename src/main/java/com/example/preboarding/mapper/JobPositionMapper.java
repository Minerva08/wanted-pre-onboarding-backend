package com.example.preboarding.mapper;


import com.example.preboarding.domain.CompanyRole;
import com.example.preboarding.domain.Date;
import com.example.preboarding.domain.JobPosition;
import com.example.preboarding.dto.request.JobPostAddReq;
import com.example.preboarding.dto.response.JobPostInfoRes;
import com.example.preboarding.repository.companyRole.CompanyRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class JobPositionMapper {

    private final CompanyRoleRepository companyRoleRepository;
    private final String formatter = "yyyy-MM-dd HH:mm:ss";


    public JobPosition toEntity(JobPostAddReq dto) {
        List<CompanyRole> companyRole = companyRoleRepository.findByCompanyNumAndRoleNum(dto.getCompanyNum(),dto.getRoleNum());

        Date date = Date.builder()
                .createDate(LocalDateTime.now())
                .startDate(LocalDateTime.parse(dto.getDate().getStartDate(), DateTimeFormatter.ofPattern(this.formatter)))
                .endDate(LocalDateTime.parse(dto.getDate().getStartDate(), DateTimeFormatter.ofPattern(this.formatter)))
                .updateDate(LocalDateTime.now())
                .build();

        return JobPosition.builder()
                .postTitle(dto.getPostTitle())
                .companyRole(companyRole.stream().findFirst().get())
                .contents(dto.getContents())
                .reward(dto.getReward())
                .skill(dto.getSkill())
                .date(date)
                .build();
    }


}
