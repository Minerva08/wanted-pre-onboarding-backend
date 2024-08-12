package com.example.preboarding.service;

import com.example.preboarding.domain.CompanyRole;
import com.example.preboarding.dto.JobRoleInfoDTO;

import java.util.List;

public interface CompanyRoleService {
   void deleteCompanyRole(Long comNum, Long roleNum);

   List<JobRoleInfoDTO>  getCompanyRoleList(Long comNum, Long roleNum);
}
