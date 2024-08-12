package com.example.preboarding.aop;

import com.example.preboarding.exception.CustomException;
import com.example.preboarding.exception.EnumResponseMessage;
import com.example.preboarding.exception.HttpErrorCode;
import com.example.preboarding.repository.companyRole.CompanyRoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class RoleValidationAspect {
    @Autowired
    private CompanyRoleRepository companyRoleRepository;
    @Autowired
    private HttpServletRequest request;

    @Pointcut("execution(* com.example.preboarding.controller.CompanyPositionController.deleteCompanyJobPostion(..))")
    public void deleteJobPositionController() {}

    @Before("deleteJobPositionController()")
    public void deleteJobPositionController(JoinPoint joinPoint){
        String comNum = request.getParameter("comNum");
        String roleNum = request.getParameter("roleNum");

        if(comNum.isEmpty() || roleNum.isEmpty()){
            CustomException ex = new CustomException(HttpErrorCode.BAD_REQUEST,EnumResponseMessage.MESSAGE_NO_REQUIRED);
            ex.addErrorDetail("comNum",request.getParameter("comNum"));
            ex.addErrorDetail("roleNum",request.getParameter("roleNum"));
            throw ex;

        }
        companyRoleRepository.findByCompanyNumAndRoleNum(Long.valueOf(comNum),Long.valueOf(roleNum)).stream().findFirst().orElseThrow(()->{
            CustomException ex = new CustomException(HttpErrorCode.BAD_REQUEST,EnumResponseMessage.MESSAGE_ER_C_ROLE);
            ex.addErrorDetail("comNum",request.getParameter("comNum"));
            ex.addErrorDetail("roleNum",request.getParameter("roleNum"));
            throw ex;
        });

    }
}
