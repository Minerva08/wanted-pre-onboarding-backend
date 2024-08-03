package com.example.preboarding.repository.role;

import com.example.preboarding.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long>, RoleRepositoryCustom {
    List<Role> findAllByCompanyNum(Long companyNum);
}
