package com.example.preboarding.repository.companyRole;

import com.example.preboarding.domain.CompanyRole;
import com.example.preboarding.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRoleRepository extends JpaRepository<CompanyRole,Long>, CompanyRoleRepositoryCustom {
    List<CompanyRole> findByCompanyNumAndRoleNum(Long comNum, Long roleNum);

}
