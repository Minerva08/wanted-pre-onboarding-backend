package com.example.preboarding.aop;

import com.example.preboarding.dto.request.ApplicationReq;
import com.example.preboarding.exception.CustomException;
import com.example.preboarding.exception.EnumResponseMessage;
import com.example.preboarding.exception.HttpErrorCode;
import com.example.preboarding.repository.apply.ApplyRepository;
import com.example.preboarding.repository.jobPosition.JobPositionRepository;
import com.example.preboarding.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
class UserValidationAspect {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobPositionRepository jobPositionRepository;
    @Autowired
    private ApplyRepository applyRepository;

    @Autowired(required = false)
    private HttpServletRequest request;

    @Pointcut("execution(* com.example.preboarding.controller.UserController.applyJobPosition(..))")
    public void userApplyController() {}
    @Pointcut("execution(* com.example.preboarding.controller.UserController.cancelApply(..))")
    public void cancelApplyController() {}
    @Pointcut("execution(* com.example.preboarding.controller.UserController.getAppliedJobPosition(..))")
    public void ApplyListController() {}

    @Before("userApplyController()")
    public void validateProductController(JoinPoint joinPoint) throws MethodArgumentNotValidException,CustomException {
        log.info("Start Validating User and JobRepository");

        // 메서드 인자 추출
        BindingResult bindingResult = null;

        // 인자 중 BindingResult 찾기
        for (Object arg : joinPoint.getArgs()) {
            Long jobPositionNum = ((ApplicationReq) arg).getJobPositionNum();
            Long userNum = ((ApplicationReq) arg).getUserNum();
            log.info("RequestInfo - jobPositionNum:{}, userNum:{}",jobPositionNum,userNum);

            if(((ApplicationReq) arg).getJobPositionNum() == null || ((ApplicationReq) arg).getUserNum() == null){
                log.error("Validation errors found: {}", bindingResult.getAllErrors());
                throw new MethodArgumentNotValidException(null, bindingResult);
            }

            userRepository.findById(userNum).orElseThrow(() -> {
                CustomException ex = new CustomException(HttpErrorCode.BAD_REQUEST, EnumResponseMessage.MESSAGE_NO_USER);
                ex.addErrorDetail("userNum",String.valueOf(userNum));
                throw ex;
            });
            jobPositionRepository.findById(jobPositionNum).orElseThrow(()->{
               CustomException ex =  new CustomException(HttpErrorCode.BAD_REQUEST, EnumResponseMessage.MESSAGE_NO_JOB_POSITION);
               ex.addErrorDetail("jobPostionNum",String.valueOf(jobPositionNum));
               throw ex;
            });

        }
        log.info("Validated Success");

    }

    @Before("cancelApplyController()")
    public void cancelApplyController(JoinPoint joinPoint) throws MethodArgumentNotValidException,CustomException {
        log.info("Validating ApplyNum");

        List<String> urlList = Arrays.stream(request.getRequestURI().split("/")).collect(Collectors.toList());
        String applyNum = urlList.get(urlList.size()-1);
        log.info("applyNum : {}",applyNum);

        applyRepository.findById(Long.valueOf(applyNum)).orElseThrow(()->{
            CustomException ex = new CustomException(HttpErrorCode.BAD_REQUEST,EnumResponseMessage.MESSAGE_NO_APPLY);
            ex.addErrorDetail("applyNum",applyNum);
            throw ex;
        });
        log.info("Validated Success");

    }

    @Before("ApplyListController()")
    public void ApplyListController(JoinPoint joinPoint) throws CustomException {
        log.info("Validating UserNum");

        List<String> collect = Arrays.stream(request.getRequestURI().split("/")).collect(Collectors.toList());
        Collections.reverse(collect);
        String userNum = collect.get(0);
        log.info("userNum : {}",userNum);

        userRepository.findById(Long.valueOf(userNum)).orElseThrow(()->{
            CustomException ex = new CustomException(HttpErrorCode.BAD_REQUEST,EnumResponseMessage.MESSAGE_NO_USER);
            ex.addErrorDetail("userNum",userNum);
            throw ex;
        });
        log.info("Validated Success");

    }

}
