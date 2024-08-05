package com.example.preboarding.service;

import com.example.preboarding.domain.CompanyRole;
import com.example.preboarding.domain.Date;
import com.example.preboarding.domain.JobPosition;
import com.example.preboarding.domain.Role;
import com.example.preboarding.dto.ApplyUserDTO;
import com.example.preboarding.dto.JobPostSearchDTO;
import com.example.preboarding.dto.SummaryJobPost;
import com.example.preboarding.dto.request.JobPositionPostReq;
import com.example.preboarding.dto.request.JobPostionAddReq;
import com.example.preboarding.dto.response.JobPostInfoRes;
import com.example.preboarding.exception.CustomException;
import com.example.preboarding.exception.EnumResponseMessage;
import com.example.preboarding.exception.HttpErrorCode;
import com.example.preboarding.mapper.JobPositionMapper;
import com.example.preboarding.repository.apply.ApplyRepository;
import com.example.preboarding.repository.companyRole.CompanyRoleRepository;
import com.example.preboarding.repository.jobPosition.JobPositionRepository;
import com.example.preboarding.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class JobPositionServiceImpl implements JobPositionsService {

    private final JobPositionRepository jobPositionRepository;
    private final CompanyRoleRepository companyRoleRepository;
    private final ApplyRepository applyRepository;
    private final JobPositionMapper jobPositionMapper;
    private final UserRepository userRepository;

    private final String formatter = "yyyy-MM-dd HH:mm:ss";


    @Override
    public Long registPosition(JobPostionAddReq postReq) {

        CompanyRole companyRole = validJobPosition(postReq.getCompanyNum(),postReq.getRoleNum());

        if(companyRole==null){
            CustomException ex = new CustomException(HttpErrorCode.CONFLICT, EnumResponseMessage.MESSAGE_ER_C_ROLE);
            ex.addErrorDetail("company's Role",null);
            throw ex;

        }else{
            JobPosition jobPosition = jobPositionMapper.toEntity(postReq);
            return jobPositionRepository.save(jobPosition).getNum();
        }

    }

    @Override
    @Transactional(readOnly = true)
    public JobPostInfoRes searchJobPostionPost(String comName, String nation, String region, String[] roleArray, Pageable pageable) {

        JobPostSearchDTO searchDTO = JobPostSearchDTO.builder()
                .comName(comName)
                .pageable(pageable)
                .roleNumList(
                        roleArray==null?
                                new ArrayList<>():
                                Arrays.stream(roleArray).toList().stream().mapToLong(Long::valueOf).boxed().toList()
                )
                .nation(nation)
                .region(region)
                .build();

        Page<JobPosition> jobPositions = jobPositionRepository.searchPosition(searchDTO);

        List<JobPostInfoRes.JobPost> jobPostList = jobPositions.stream()
                .map(JobPostInfoRes.JobPost::new) // JobPosition 객체를 JobPost 객체로 변환
                .collect(Collectors.toList());

        // JobPostInfoRes 객체 생성
        return JobPostInfoRes.builder()
                .jobPostList(jobPostList)
                .build();

    }

    @Override
    @Transactional(readOnly = true)
    public JobPostInfoRes getJobPostDetail(Long postNum) {
        JobPosition jobPostDetail = validJobPost(postNum);

        List<SummaryJobPost> otherPositionList = jobPositionRepository.findCompanyOtherPosition(jobPostDetail.getCompany().getComNum(), jobPostDetail.getNum())
                .stream().map(SummaryJobPost::new).toList();

        return JobPostInfoRes.builder()
                .detail(JobPostInfoRes.JobPostDetail.builder().jobPosition(jobPostDetail).build())
                .otherPostList(otherPositionList).build();
    }

    private JobPosition validJobPost(Long postNum) {
        JobPosition jobPostDetail = jobPositionRepository.findById(postNum).orElseThrow(() -> {
            CustomException ex = new CustomException(HttpErrorCode.BAD_REQUEST, EnumResponseMessage.MESSAGE_NO_JOB_POSITION);
            ex.addErrorDetail("postNum", String.valueOf(postNum));
            throw ex;
        });
        return jobPostDetail;
    }

    @Override
    public Long updateJobPostInfo(Long postNum, JobPositionPostReq modJobPost) {

        AtomicReference<Long> updatePostNum = new AtomicReference<>(0l);


        JobPosition existingJobPosition = jobPositionRepository.findById(postNum).get();

        List<JobPosition> otherPositionList = jobPositionRepository.findCompanyOtherPosition(existingJobPosition.getCompany().getComNum(), existingJobPosition.getNum());

        otherPositionList.stream()
                .filter(e -> e.getCompanyRole().getRole().getRoleNum().equals(modJobPost.getRoleNum()))
                .findFirst()
                .ifPresentOrElse(otherPost->{
                        CustomException ex = new CustomException(HttpErrorCode.INTER_SERVER_ERROR,EnumResponseMessage.MESSAGE_ER_AL_JOB_POSITION);
                        ex.addErrorDetail("roleNum",String.valueOf(modJobPost.getRoleNum()));
                        throw  ex;
                        },

                        ()->{
                            Role updateRole = Role.builder().roleNum(modJobPost.getRoleNum()).build();

                            Long byCompanyNumAndRoleNum = companyRoleRepository.findByCompanyNumAndRoleNum(existingJobPosition.getCompany().getComNum(), updateRole.getRoleNum()).stream().findFirst().orElseThrow(()->{
                                CustomException ex = new CustomException(HttpErrorCode.BAD_REQUEST,EnumResponseMessage.MESSAGE_ER_C_ROLE);
                                ex.addErrorDetail("roleNum",String.valueOf(modJobPost.getRoleNum()));
                                throw ex;
                            }).getCompanyRoleNum();

                            CompanyRole modCompanyRole = CompanyRole.builder().num(byCompanyNumAndRoleNum).role(updateRole).company(existingJobPosition.getCompany()).build();


                            JobPosition updateInfo = JobPosition.builder()
                                    .postNum(existingJobPosition.getNum())
                                    .postTitle(modJobPost.getPostTitle())
                                    .companyRole(modCompanyRole)
                                    .contents(modJobPost.getContents())
                                    .skill(modJobPost.getSkill())
                                    .date(Date.builder()
                                            .createDate(existingJobPosition.getDate().getCreateDate())
                                            .startDate(LocalDateTime.parse(modJobPost.getDate().getStartDate(), DateTimeFormatter.ofPattern(this.formatter)))
                                            .endDate(LocalDateTime.parse(modJobPost.getDate().getEndDate(), DateTimeFormatter.ofPattern(this.formatter)))
                                            .updateDate(LocalDateTime.now())
                                            .build())
                                    .reward(modJobPost.getReward())
                                    .applyCnt(existingJobPosition.getApplyCnt())
                                    .build();

                            JobPosition save = jobPositionRepository.save(updateInfo);
                            updatePostNum.set(save.getNum());

                        }
                );

        return updatePostNum.get();
    }

    @Override
    public void deleteJobPost(Long postNum) {

        List<ApplyUserDTO> applies = applyRepository.findByApplyNum(postNum);
        applies.stream().forEach(e -> {
            Long l = userRepository.updateApplyStatus(e.getUserNum(), false);
            System.out.println("userNum:"+l);
        });
        jobPositionRepository.deleteById(postNum);


    }

    private CompanyRole validJobPosition(long comNum, long roleNum) {

        List<CompanyRole> byCompanyNumAndRoleNum = companyRoleRepository.findByCompanyNumAndRoleNum(comNum, roleNum);

        if(byCompanyNumAndRoleNum==null){
            CustomException ex = new CustomException(HttpErrorCode.INTER_SERVER_ERROR,EnumResponseMessage.MESSAGE_ER_C_ROLE);
            ex.addErrorDetail("comNum",String.valueOf(comNum));
            ex.addErrorDetail("roleNum",String.valueOf(roleNum));
            throw ex;
        }

        return byCompanyNumAndRoleNum.stream().findFirst().get();
    }
}
