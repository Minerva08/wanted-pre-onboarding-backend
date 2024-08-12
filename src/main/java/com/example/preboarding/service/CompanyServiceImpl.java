package com.example.preboarding.service;

import com.example.preboarding.domain.Company;
import com.example.preboarding.domain.Role;
import com.example.preboarding.dto.request.CompanyReq;
import com.example.preboarding.mapper.CompanyMapper;
import com.example.preboarding.repository.company.CompanyRepository;
import com.example.preboarding.repository.role.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyMapper companyMapper;
    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;
    @Override
    public Long addCompany(CompanyReq registCom) {
        Company addCom = companyMapper.toEntity(registCom);

        List<Role> allRoleList = roleRepository.findAll();
        allRoleList.stream().forEach(e -> {
            addCom.addRole(e);
        });

        return companyRepository.save(addCom).getComNum();

    }

    @Override
    public void deleteCompany(Long comNum) {
        companyRepository.deleteById(comNum);
    }
}
