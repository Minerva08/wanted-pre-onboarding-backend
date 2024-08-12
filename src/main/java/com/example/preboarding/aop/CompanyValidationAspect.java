package com.example.preboarding.aop;

import com.example.preboarding.dto.request.CompanyReq;
import com.example.preboarding.exception.CustomException;
import com.example.preboarding.exception.EnumResponseMessage;
import com.example.preboarding.exception.HttpErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class CompanyValidationAspect {

    @Pointcut("execution(* com.example.preboarding.controller.CompanyController.registCompnay(..))")
    public void registController() {}

    @Before("registController()")
    public void registController(JoinPoint joinPoint) throws CustomException {
        log.info("Start Validating CompanyReq");

        // 인자 중 BindingResult 찾기
        for (Object arg : joinPoint.getArgs()) {

            String comId = ((CompanyReq) arg).getComId();
            String comName = ((CompanyReq) arg).getComName();
            String nation = ((CompanyReq) arg).getNation();
            String region = ((CompanyReq) arg).getRegion();

            if(comId==null || comName==null || nation==null || region==null ){
                CustomException ex = new CustomException(HttpErrorCode.BAD_REQUEST, EnumResponseMessage.MESSAGE_NO_REQUIRED);
                ex.addErrorDetail("comId",comId);
                ex.addErrorDetail("comName",comName);
                ex.addErrorDetail("nation",nation);
                ex.addErrorDetail("region",region);
                throw ex;
            }

            log.info("Success Validated regist company.");
        }

    }

}

