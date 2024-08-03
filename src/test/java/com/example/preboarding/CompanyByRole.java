package com.example.preboarding;

import com.example.preboarding.domain.Company;
import com.example.preboarding.domain.CompanyRole;
import com.example.preboarding.domain.JobPosition;
import com.example.preboarding.domain.Role;
import com.example.preboarding.repository.company.CompanyRepository;
import com.example.preboarding.repository.companyRole.CompanyRoleRepository;
import com.example.preboarding.repository.jobPosition.JobPositionRepository;
import com.example.preboarding.repository.role.RoleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CompanyByRole {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CompanyRoleRepository companyRoleRepository;

    @Autowired
    private JobPositionRepository jobPositionRepository;
    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("회사 등록")
    public void addCompany(){
        String comId = "3O3";
        String comName="삼쩜삼";
        String nation="한국";
        String region="서울시 강남구";

        Company addCom = Company.builder()
                .comId(comId)
                .comName(comName)
                .nation(nation)
                .region(region)
                .build();

        List<Role> allRoleList = roleRepository.findAll();
        allRoleList.stream().forEach(e -> {
            addCom.addRole(e);
        });

        Company saveCom = companyRepository.save(addCom);
        assertEquals(comId,saveCom.getComId());
        assertEquals(comName,saveCom.getComName());
        assertEquals(nation,saveCom.getNation());
        assertEquals(region,saveCom.getRegion());

    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("직무 등록")
    public void addRole(){
        String roleId="FE";
        String roleName="필드엔지니어";

        Role addRole = Role.builder()
                .roleId(roleId)
                .roleName(roleName)
                .build();

        List<Company> allCompany = companyRepository.findAll();
        allCompany.forEach(e->{
            addRole.addCompany(e);
        });

        Role saveRole = roleRepository.save(addRole);

        assertNotNull(saveRole.getRoleNum(),"직무가 등록되지 않았습니다");
        assertEquals(roleId, saveRole.getRoleId());
        assertEquals(roleName, saveRole.getRoleName());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("회사 삭제")
    public void removeCompany(){
        Long deleteComNum = 2l;

        companyRepository.deleteById(deleteComNum);
        CompanyRole byCompanyNumAndRoleNum = companyRoleRepository.findByCompanyNumAndRoleNum(deleteComNum, null);
        assertNull(byCompanyNumAndRoleNum,"회사의 직무가 존재합니다");

        List<JobPosition> companyOtherPosition = jobPositionRepository.findCompanyOtherPosition(deleteComNum, null);
        assert (companyOtherPosition.size()==0);

        Optional<Company> byId = companyRepository.findById(deleteComNum);
        boolean present = byId.isPresent();
        assertFalse(present,"해당 회사가 삭제 되지 않았습니다");
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("사내 해당 직무 및 등록된 채용 공고 삭제")
    public void removePositionByCompany(){

        Long deleteCompanyRoleNum = companyRoleRepository.findByCompanyNumAndRoleNum(1l, 16l).getCompanyRoleNum();

        companyRoleRepository.deleteById(deleteCompanyRoleNum);

        List<JobPosition> jobPositionList = jobPositionRepository.findAll();
        jobPositionList.stream().filter(e -> e.getCompanyRole().getCompanyRoleNum().equals(deleteCompanyRoleNum)).findFirst().ifPresentOrElse(
                jobPosition -> {
                    assertEquals(deleteCompanyRoleNum,jobPosition.getCompanyRole().getCompanyRoleNum());
                },
                ()->{
                    throw new NoSuchElementException("delete companyRoleNum : "+deleteCompanyRoleNum);
                }
        );
    }



}
