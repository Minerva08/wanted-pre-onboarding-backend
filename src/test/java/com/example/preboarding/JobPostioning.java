package com.example.preboarding;

import com.example.preboarding.domain.*;
import com.example.preboarding.repository.CompanyRoleRepository;
import com.example.preboarding.repository.JobPositionRepository;
import com.example.preboarding.service.JobPositionsService;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class JobPostioning {

    @Autowired
    private JobPositionsService jobPositionService;
    @Autowired
    private JobPositionRepository jobPositionRepository;
    @Autowired
    private CompanyRoleRepository companyRoleRepository;

    @Test
    @Rollback(value = false)
    @Transactional
    @DisplayName("1. 채용 공고 등록")
    public void register(){
        Date date = new Date();
        date.setCreateDate(LocalDateTime.of(2024,8,1,9,0));
        date.setUpdateDate(LocalDateTime.of(2024,8,1,9,0));
        date.setStartDate(LocalDateTime.of(2024,8,1,11,0));
        date.setEndDate(LocalDateTime.of(2024,8,5,23,0));


        JobPosition jobPosition =JobPosition.builder()
                .reward(500000)
                .postTitle("네이버 서버 개발자")
                .contents("3년 이상의 서버 개발 경험...")
                .companyRole(companyRoleRepository.findById(2l).get())
                .date(date)
                .build();

        Long postNum = jobPositionService.registPosition(jobPosition);
        JobPosition jobPositionDetail = jobPositionRepository.findByIdDetail(postNum);

        assert(jobPositionDetail).getNum().equals(postNum);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("2. 채용 공고 수정")
    public void modifyJobPosition(){
        JobPosition existingJobPosition = jobPositionRepository.findById(15l).get();
        CompanyRole companyRole = companyRoleRepository.findById(existingJobPosition.getNum()).get();
        Company company = companyRole.getCompany();
        Role updateRole = Role.builder().roleNum(3l).build();
        Long byCompanyNumAndRoleNum = companyRoleRepository.findByCompanyNumAndRoleNum(company.getComNum(), updateRole.getRoleNum());


        CompanyRole modCompanyRole = CompanyRole.builder().num(byCompanyNumAndRoleNum).role(updateRole).company(company).build();

        String modSkill = new StringBuilder().append("java").append(",").append("MySQL").toString();
        String modContents = "2년차 백엔드";
        String modTitle = "서버 개발자";

        Date modDate = new Date();
        modDate.setUpdateDate(LocalDateTime.now());
        modDate.setStartDate(existingJobPosition.getDate().getStartDate().plusDays(2));
        modDate.setEndDate(existingJobPosition.getDate().getEndDate().plusDays(7));
        modDate.setCreateDate(existingJobPosition.getDate().getCreateDate());

        System.out.println("startDate : "+existingJobPosition.getDate().getStartDate());
        System.out.println("endDate : "+existingJobPosition.getDate().getStartDate());



        JobPosition updateInfo = JobPosition.builder()
                .postNum(existingJobPosition.getNum())
                .postTitle(modTitle)
                .companyRole(modCompanyRole)
                .contents(modContents)
                .skill(modSkill)
                .date(modDate)
                .reward(existingJobPosition.getReward())
                .applyCnt(existingJobPosition.getApplyCnt())
                .build();

        JobPosition save = jobPositionRepository.save(updateInfo);
        assertEquals(existingJobPosition.getNum(),save.getNum());
        assertEquals(modContents,save.getContents(),"채용 공고 내용이 변경되었습니다");
        assertEquals(modDate.getStartDate(),save.getDate().getStartDate(),"채용 시작 일정이 변경되었습니다");
        assertEquals(modDate.getEndDate(),save.getDate().getEndDate(),"채용 종료 일정이 변경되었습니다");
        assertEquals(modDate.getUpdateDate(),save.getDate().getUpdateDate(),"채용 공고 수정 일자가 변경되었습니다");
        assertEquals(modTitle, save.getPostTitle(), "제목이 수정되지 않았습니다.");
        assertEquals(modContents, save.getContents(), "내용이 수정되지 않았습니다.");
        assertEquals(modSkill,save.getSkill(), "기술 스택이 수정되지 않았습니다.");



    }
    @Test
    @Rollback(value = false)
    @Transactional
    @DisplayName("3. 채용 공고 삭제")
    public void deleteJobPosition() {
        jobPositionRepository.deleteById(13l);
        List<JobPosition> jobPositionList = jobPositionRepository.findAll();
        jobPositionList.stream().forEach(e -> System.out.println(e.getNum()));
    }


    @Test
    @Transactional(readOnly = true)
    @DisplayName("4-1. 채용 공고 목록")
    public void getJobPostionList(){
        List<JobPosition> jobPositionList = jobPositionRepository.findAll();
        jobPositionList.forEach(
                e -> {
                    System.out.println("postNum : "+e.getNum());
                    System.out.println("postTitle : "+e.getPostTitle());
                    System.out.println("companyNum : "+e.getCompany().getComNum());
                    System.out.println("companyName : "+e.getCompany().getComName());
                }

        );
    }

    @Test
    @Transactional(readOnly = true)
    @DisplayName("4-2. 채용 공고 조회 및 검색(pagenation)")
    public void searchJobPositionList(){
        String comName = "카카오";
        List<Long>  roleNum = new ArrayList<>();
        roleNum.add(3l);
        roleNum.add(9l);
        String national = "한국";
        String region="강남구";
        Pageable pageable = PageRequest.of(0, 10);
        Page<JobPosition> searchJobPositions = jobPositionRepository.searchPosition(comName, roleNum, pageable);

        for (JobPosition searchJobPosition : searchJobPositions) {
            assertThat(roleNum,hasItem(searchJobPosition.getCompanyRole().getRole().getRoleNum()));
            assertThat(searchJobPosition.getCompany().getComName(),containsString(comName));
            System.out.println("josPost CompanyName: "+searchJobPosition.getCompany().getComName());
            System.out.println("josPostTitle: "+searchJobPosition.getPostTitle());
        }
    }


    @Test
    @Transactional(readOnly = true)
    @DisplayName("5. 채용 공고 상세 보기")
    public void getJobPositionDetail(){
        Long postNum = 17l;
        JobPosition jobPositionDetail = jobPositionRepository.findByIdDetail(postNum);
        assertEquals(jobPositionDetail.getNum(),postNum,"조회한 채용 공고의 번호가 다릅니다");
        System.out.println("JobPositionCompanyInfo"+jobPositionDetail.getCompany().toString());
        System.out.println("JobPositionRoleInfo"+jobPositionDetail.getCompanyRole().getRole().toString());
        System.out.println("during Date: "+jobPositionDetail.getDate().getStartDate()+" ~ "+jobPositionDetail.getDate().getEndDate());
        System.out.println("contents :"+jobPositionDetail.getContents());
        System.out.println("skill : "+jobPositionDetail.getSkill());
    }

    @Test
    @Transactional(readOnly = true)
    @DisplayName("5-1. 사내 다른 직무 채용 공고 목록")
    public void getCompanyOtherPosition(){

        Long postNum = 17l;
        Long comNum=2l;

        List<JobPosition> otherJobPositionByCompany = jobPositionRepository.findCompanyOtherPosition(comNum, postNum);
        int cnt = otherJobPositionByCompany.size();
        System.out.println("totalCnt1 : "+ cnt);

        for (JobPosition otherPositionList1 : otherJobPositionByCompany) {
            System.out.println("================================================================");
            System.out.println("CompanyNum : "+otherPositionList1.getCompany().getComNum());
            System.out.println("CompanyName : "+otherPositionList1.getCompany().getComName());
            System.out.println("CompanyPositioning roleNum : "+otherPositionList1.getCompanyRole().getRole().getRoleNum());
            System.out.println("CompanyPositioning roleName : "+otherPositionList1.getCompanyRole().getRole().getRoleName());
            System.out.println("================================================================");

        }

        JobPosition jobPosition = jobPositionRepository.findById(postNum).get();

        List<JobPosition> otherPositionList2 = jobPosition.getCompany().getJobPositionList().stream().filter(jobPosition1 -> !jobPosition1.getNum().equals(postNum)).toList();
        System.out.println("totalCnt2 : "+ otherPositionList2.size());

        for (JobPosition position : otherPositionList2) {
            System.out.println("================================================================");
            System.out.println("CompanyNum : "+position.getCompany().getComNum());
            System.out.println("CompanyName : "+position.getCompany().getComName());
            System.out.println("CompanyPositioning roleNum : "+position.getCompanyRole().getRole().getRoleNum());
            System.out.println("CompanyPositioning roleName : "+position.getCompanyRole().getRole().getRoleName());
            System.out.println("================================================================");

        }


    }

    @Test
    @Transactional(readOnly = true)
    @DisplayName("+회사별 직무 목록")
    public void roleByCompany(){
        Long comNum = 2l;
        List<CompanyRole> companyRole = companyRoleRepository.findAllByCompanyNum(comNum);
        companyRole.stream().forEach(e->
                {
                    System.out.println("comNum"+e.getCompany().getComNum());
                    assertEquals(comNum,e.getCompany().getComNum());
                    System.out.println("companyRoleNum : "+e.getCompanyRoleNum());
                    System.out.println("roleNum : "+e.getRole().getRoleNum());

                }
        );

    }

}
