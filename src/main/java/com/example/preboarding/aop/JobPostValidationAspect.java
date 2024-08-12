package com.example.preboarding.aop;

import com.example.preboarding.dto.request.JobPostReq;
import com.example.preboarding.dto.request.JobPostAddReq;
import com.example.preboarding.exception.CustomException;
import com.example.preboarding.exception.EnumResponseMessage;
import com.example.preboarding.exception.HttpErrorCode;
import com.example.preboarding.repository.companyRole.CompanyRoleRepository;
import com.example.preboarding.repository.jobPosition.JobPositionRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Aspect
@Component
@Slf4j
public class JobPostValidationAspect {
    @Autowired
    private CompanyRoleRepository companyRoleRepository;
    @Autowired
    private JobPositionRepository jobPositionRepository;
    @Autowired(required = false)
    private HttpServletRequest request;

    @Pointcut("execution(* com.example.preboarding.controller.JobPositionController.registJobPositionPost(..))")
    public void registController() {}
    @Pointcut("execution(* com.example.preboarding.controller.JobPositionController.modifyJobPost(..))")
    public void updateController() {}

    @Pointcut("execution(* com.example.preboarding.controller.JobPositionController.getDetail(..))")
    public void detailController() {}

    @Before("registController()")
    public void registController(JoinPoint joinPoint) throws CustomException {
        log.info("Start Validating JobPositionAddReq");

        // 인자 중 BindingResult 찾기
        for (Object arg : joinPoint.getArgs()) {

            Long companyNum = ((JobPostAddReq) arg).getCompanyNum();
            Long jobPostRoleNum = ((JobPostAddReq) arg).getRoleNum();
            String jobPostTitle = ((JobPostAddReq) arg).getPostTitle();
            String jobPostStartDate = ((JobPostAddReq) arg).getDate().getStartDate();
            String jobPostEndDate = ((JobPostAddReq) arg).getDate().getEndDate();

            if(companyNum==null || jobPostTitle==null || jobPostTitle==null || jobPostEndDate==null || jobPostStartDate==null){
                CustomException ex = new CustomException(HttpErrorCode.BAD_REQUEST,EnumResponseMessage.MESSAGE_NO_REQUIRED);
                ex.addErrorDetail("roleNum",String.valueOf(jobPostRoleNum));
                ex.addErrorDetail("companyNum",String.valueOf(companyNum));
                ex.addErrorDetail("jobPostTitle",jobPostTitle);
                ex.addErrorDetail("jobPostStartDate",jobPostStartDate);
                ex.addErrorDetail("jotPostEndDate",jobPostEndDate);
                throw ex;
            }

            companyRoleRepository.findByCompanyNumAndRoleNum(companyNum, jobPostRoleNum).stream().findFirst().ifPresentOrElse(
                    companyRole -> {
                        log.info("JobPositionAddReq is All Required and Add Role is exist in Company.");
                    },
                    ()->{
                        log.error("JobPosition is not exist in company. comNum:{}, roleNum:{}",companyNum,jobPostRoleNum);
                        CustomException ex = new CustomException(HttpErrorCode.BAD_REQUEST,EnumResponseMessage.MESSAGE_ER_C_ROLE);
                        ex.addErrorDetail("companyNum",String.valueOf(companyNum));
                        ex.addErrorDetail("roleNum",String.valueOf(jobPostRoleNum));
                        throw ex;
                    }
            );

        }

        log.info("Success Validated regist jobPost.");

    }


    @Before("updateController()")
    public void updateController(JoinPoint joinPoint) throws CustomException {

        validJobPostNum(request);


        for (Object arg : joinPoint.getArgs()) {

            Long jobPostRoleNum = ((JobPostReq) arg).getRoleNum();
            String jobPostTitle = ((JobPostReq) arg).getPostTitle();
            String jobPostStartDate = ((JobPostReq) arg).getDate().getStartDate();
            String jobPostEndDate = ((JobPostReq) arg).getDate().getEndDate();

            if( jobPostTitle==null || jobPostTitle==null || jobPostEndDate==null || jobPostStartDate==null){
                CustomException ex = new CustomException(HttpErrorCode.BAD_REQUEST,EnumResponseMessage.MESSAGE_NO_REQUIRED);
                ex.addErrorDetail("roleNum",String.valueOf(jobPostRoleNum));
                ex.addErrorDetail("jobPostTitle",jobPostTitle);
                ex.addErrorDetail("jobPostStartDate",jobPostStartDate);
                ex.addErrorDetail("jotPostEndDate",jobPostEndDate);
                throw ex;
            }

        }
        log.info("Success Validated update JobPost.");

    }

    @Before("detailController()")
    public void detailController(JoinPoint joinPoint) throws CustomException {
        validJobPostNum(request);
    }
    private void validJobPostNum(HttpServletRequest request) {
        List<String> urlList = Arrays.stream(request.getRequestURI().split("/")).toList();
        Collections.reverse(urlList);
        String postNum = urlList.get(0);
        log.info("Valid postNum : {}",postNum);

        jobPositionRepository.findById(Long.valueOf(postNum)).orElseThrow(()->{
            CustomException ex = new CustomException(HttpErrorCode.BAD_REQUEST,EnumResponseMessage.MESSAGE_NO_JOB_POSITION);
            ex.addErrorDetail("postNum",postNum);
            throw ex;
        });
    }
}
