package com.example.preboarding;

import com.example.preboarding.domain.*;
import com.example.preboarding.dto.JobPostSearchDTO;
import com.example.preboarding.repository.companyRole.CompanyRoleRepository;
import com.example.preboarding.repository.jobPosition.JobPositionRepository;
import com.example.preboarding.repository.role.RoleRepository;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class JobPostioning {
    
    @Autowired
    private JobPositionRepository jobPositionRepository;
    @Autowired
    private CompanyRoleRepository companyRoleRepository;
    @Autowired
    private RoleRepository roleRepository;

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

        Long comNum = 3l;

        List<Role> allRoleByCompany = roleRepository.findAllByCompanyNum(comNum);

        if(allRoleByCompany.size() > 0) {

            allRoleByCompany.stream().forEach(e -> {
                System.out.println("회사가 공고를 올리지 않은 직무 : " + e.getRoleNum() + "," + e.getRoleId() + "," + e.getRoleName());
            });

            List<CompanyRole> registCompanyRole = companyRoleRepository.findByCompanyNumAndRoleNum(comNum, allRoleByCompany.get(0).getRoleNum());

            registCompanyRole.forEach(e->{

                System.out.println("companyRoleNum : " + e.getCompanyRoleNum());
                System.out.println("companyNum:" + e.getCompany().getComNum());
                System.out.println("roleNum:" + e.getRole().getRoleNum());
            });


            JobPosition jobPosition = JobPosition.builder()
                    .reward(500000)
                    .postTitle("FE 채용")
                    .contents("필드 엔지니어")
                    .companyRole(registCompanyRole.stream().findFirst().get())
                    .date(date)
                    .build();

            JobPosition save = jobPositionRepository.save(jobPosition);
            assertNotNull(save, "채용공고가 등록되지 않았습니다");
        }else{
            System.out.println("채용 공고로 등록되지 않은 직무가 없습니다");
        }
    }

    @Test
    @Rollback(value = false)
    @DisplayName("2. 채용 공고 수정")
    public void modifyJobPosition(){
        Long changeRoleNum = 16l;

        JobPosition existingJobPosition = jobPositionRepository.findById(23l).get();

        List<JobPosition> otherPositionList = jobPositionRepository.findCompanyOtherPosition(existingJobPosition.getCompany().getComNum(), existingJobPosition.getNum());

        Optional<JobPosition> already = otherPositionList.stream()
                .filter(e -> e.getCompanyRole().getRole().getRoleNum().equals(changeRoleNum))
                .findFirst();

        if(already.isPresent()){
            System.out.println("이미 등록된 공고 : "+already.get().getNum());

        }else{

            Role updateRole = Role.builder().roleNum(changeRoleNum).build();
            Long byCompanyNumAndRoleNum = companyRoleRepository.findByCompanyNumAndRoleNum(existingJobPosition.getCompany().getComNum(), updateRole.getRoleNum()).stream().findFirst().get().getCompanyRoleNum();

            CompanyRole modCompanyRole = CompanyRole.builder().num(byCompanyNumAndRoleNum).role(updateRole).company(existingJobPosition.getCompany()).build();

            String modSkill = new StringBuilder().append("linux").append(",").append("onPremiss").toString();
            String modContents = "5년 이상 경력 \n 병원 관련 도메인 업무 경험 우대";
            String modTitle = "IOT 스마트 병동 필드엔지니어 모집";

            Date modDate = Date.builder()
                    .startDate(existingJobPosition.getDate().getStartDate().plusDays(3))
                    .endDate(existingJobPosition.getDate().getStartDate().plusDays(3))
                    .createDate(existingJobPosition.getDate().getCreateDate())
                    .updateDate(LocalDateTime.now())
                    .build();

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
            assertEquals(existingJobPosition.getCompany().getComNum(),save.getCompany().getComNum(),"회사정보가 변경되었습니다");
            assertEquals(modContents,save.getContents(),"채용 공고 내용이 변경되었습니다");
            assertEquals(modDate.getStartDate(),save.getDate().getStartDate(),"채용 시작 일정이 변경되었습니다");
            assertEquals(modDate.getEndDate(),save.getDate().getEndDate(),"채용 종료 일정이 변경되었습니다");
            assertEquals(modDate.getUpdateDate(),save.getDate().getUpdateDate(),"채용 공고 수정 일자가 변경되었습니다");
            assertEquals(modTitle, save.getPostTitle(), "제목이 수정되지 않았습니다.");
            assertEquals(modContents, save.getContents(), "내용이 수정되지 않았습니다.");
            assertEquals(modSkill,save.getSkill(), "기술 스택이 수정되지 않았습니다.");
        }


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
        int[] roleArray = new int[] {3, 9};
        String nation = "한국";
        String region="강남구";
        Pageable pageable = PageRequest.of(0, 10);

        JobPostSearchDTO searchDto = JobPostSearchDTO.builder()
                .comName(comName)
                .pageable(pageable)
                .roleNumList(Arrays.stream(roleArray)
                        .mapToObj(Long::valueOf)
                        .collect(Collectors.toList())
                )
                .nation(nation)
                .region(region)
                .build();
        Page<JobPosition> searchJobPositions = jobPositionRepository.searchPosition(searchDto);

        for (JobPosition searchJobPosition : searchJobPositions) {
            assertThat(Arrays.asList(roleArray),hasItem(searchJobPosition.getCompanyRole().getRole().getRoleNum()));
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
        JobPosition jobPositionDetail = jobPositionRepository.findById(postNum).get();
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

        Long postNum = 8l;
        Long comNum=1l;

        List<JobPosition> otherPositionList1 = jobPositionRepository.findCompanyOtherPosition(comNum, postNum);
        int cnt = otherPositionList1.size();
        System.out.println("totalCnt1 : "+ cnt);

        for (JobPosition position : otherPositionList1) {
            System.out.println("CompanyNum : "+position.getCompany().getComNum());
            System.out.println("CompanyName : "+position.getCompany().getComName());
            System.out.println("CompanyPositioning roleNum : "+position.getCompanyRole().getRole().getRoleNum());
            System.out.println("CompanyPositioning roleName : "+position.getCompanyRole().getRole().getRoleName());

        }

    }

}
