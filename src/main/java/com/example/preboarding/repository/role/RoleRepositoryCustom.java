package com.example.preboarding.repository.role;


import com.example.preboarding.domain.Role;

import java.util.List;

public interface RoleRepositoryCustom {
    List<Role> findAllByCompanyNum(Long companyNum);

}
