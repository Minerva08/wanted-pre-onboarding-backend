package com.example.preboarding.service;

import com.example.preboarding.domain.CompanyRole;

import java.util.List;

public interface CompanyRoleService {
   void deleteCompanyRole(Long comNum, Long roleNum);

   List<CompanyRole>  getCompanyRoleList(Long comNum, Long roleNum);
}
