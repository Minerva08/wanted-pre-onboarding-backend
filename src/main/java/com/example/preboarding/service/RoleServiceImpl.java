package com.example.preboarding.service;

import com.example.preboarding.domain.Company;
import com.example.preboarding.domain.Role;
import com.example.preboarding.dto.request.RoleReq;
import com.example.preboarding.mapper.RoleMapper;
import com.example.preboarding.repository.company.CompanyRepository;
import com.example.preboarding.repository.role.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final RoleMapper roleMapper;


    @Override
    public Long addRole(RoleReq registRole) {

        Role addRole = roleMapper.toEntity(registRole);

        List<Company> allCompany = companyRepository.findAll();
        allCompany.forEach(e->{
            addRole.addCompany(e);
        });

        return roleRepository.save(addRole).getRoleNum();
    }
}
