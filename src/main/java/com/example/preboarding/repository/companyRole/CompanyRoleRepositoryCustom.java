package com.example.preboarding.repository.companyRole;

import com.example.preboarding.domain.CompanyRole;
import com.example.preboarding.domain.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRoleRepositoryCustom {
    List<CompanyRole> findByCompanyNumAndRoleNum(Long comNum, Long roleNum);

}
