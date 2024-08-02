package com.example.preboarding.repository;

import com.example.preboarding.domain.CompanyRole;
import com.example.preboarding.domain.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRoleRepositoryCustom {
    CompanyRole findCompanyRoleById(Long id);
    Long findByCompanyNumAndRoleNum(Long comNum, Long roleNum);

}
