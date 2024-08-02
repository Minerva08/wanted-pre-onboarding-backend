package com.example.preboarding.repository;

import com.example.preboarding.domain.CompanyRole;
import com.example.preboarding.domain.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRoleRepository extends JpaRepository<CompanyRole,Long>, CompanyRoleRepositoryCustom {
    CompanyRole findCompanyRoleById(Long id);
    Long findByCompanyNumAndRoleNum(Long comNum, Long roleNum);

    @Query("SELECT cr FROM CompanyRole cr WHERE cr.company.comNum = :companyNum")
    List<CompanyRole> findAllByCompanyNum(@Param("companyNum") Long companyNum);
}
