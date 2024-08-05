package com.example.preboarding.service;

import com.example.preboarding.domain.CompanyRole;
import com.example.preboarding.repository.companyRole.CompanyRoleRepository;
import com.example.preboarding.repository.jobPosition.JobPositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
@Transactional
public class CompanyRoleServiceImpl implements CompanyRoleService {
    private final CompanyRoleRepository companyRoleRepository;


    @Override
    public void deleteCompanyRole(Long comNum, Long roleNum) {

        Long deleteCompanyRoleNum = companyRoleRepository.findByCompanyNumAndRoleNum(1l, 16l).stream().findFirst().get().getCompanyRoleNum();
        companyRoleRepository.deleteById(deleteCompanyRoleNum);

    }

    @Override
    public List<CompanyRole> getCompanyRoleList(Long comNum, Long roleNum) {
        return companyRoleRepository.findByCompanyNumAndRoleNum(comNum, null);

    }

}
