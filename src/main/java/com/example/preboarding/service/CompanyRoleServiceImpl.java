package com.example.preboarding.service;

import com.example.preboarding.domain.CompanyRole;
import com.example.preboarding.dto.JobRoleInfoDTO;
import com.example.preboarding.mapper.RoleMapper;
import com.example.preboarding.repository.companyRole.CompanyRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
@Transactional
public class CompanyRoleServiceImpl implements CompanyRoleService {
    private final CompanyRoleRepository companyRoleRepository;
    private final RoleMapper roleMapper;


    @Override
    public void deleteCompanyRole(Long comNum, Long roleNum) {

        Long deleteCompanyRoleNum = companyRoleRepository.findByCompanyNumAndRoleNum(comNum, roleNum).stream().findFirst().get().getCompanyRoleNum();
        companyRoleRepository.deleteById(deleteCompanyRoleNum);

    }

    @Override
    public List<JobRoleInfoDTO> getCompanyRoleList(Long comNum, Long roleNum) {
        List<CompanyRole> byCompanyRoleList = companyRoleRepository.findByCompanyNumAndRoleNum(comNum, roleNum);
        return roleMapper.toDto(byCompanyRoleList);
    }

}
