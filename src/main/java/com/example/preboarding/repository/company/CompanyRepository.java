package com.example.preboarding.repository.company;

import com.example.preboarding.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    Company findByComId(String comId);
}
