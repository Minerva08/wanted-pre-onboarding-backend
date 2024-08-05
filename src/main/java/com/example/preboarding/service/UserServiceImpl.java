package com.example.preboarding.service;

import com.example.preboarding.domain.Apply;
import com.example.preboarding.domain.JobPosition;
import com.example.preboarding.domain.User;
import com.example.preboarding.dto.ApplyInfoDTO;
import com.example.preboarding.dto.request.ApplicationReq;
import com.example.preboarding.dto.response.ApplicationRes;
import com.example.preboarding.exception.CustomException;
import com.example.preboarding.exception.EnumResponseMessage;
import com.example.preboarding.exception.HttpErrorCode;
import com.example.preboarding.repository.apply.ApplyRepository;
import com.example.preboarding.repository.jobPosition.JobPositionRepository;
import com.example.preboarding.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final ApplyRepository applyRepository;
    private final UserRepository userRepository;
    private final JobPositionRepository jobPositionRepository;

    @Override
    public ApplicationRes applyJob(ApplicationReq applyRequest) {

        if(validateUser(applyRequest.getUserNum(),true)
                && validateJobPosition(applyRequest.getJobPositionNum())){
            User applier = userRepository.findById(applyRequest.getUserNum()).get();
            JobPosition jobPosition = jobPositionRepository.findById(applyRequest.getJobPositionNum()).get();

            Apply apply = Apply.builder()
                    .user(applier)
                    .jobPosition(jobPosition)
                    .applyDate(LocalDateTime.now())
                    .build();

            Apply saveApplyInfo = applyRepository.save(apply);
            userRepository.updateApplyStatus(applier.getUserNum(), true);

            jobPosition.incrementApplyCnt();
            jobPositionRepository.save(jobPosition);

            return ApplicationRes.builder()
                    .userId(applier.getUserId())
                    .applyStatus(true)
                    .jobPostNum(saveApplyInfo.getJobPosition().getNum())
                    .applyNum(saveApplyInfo.getApplyNum())
                    .build();

        }
        return null;
    }

    @Override
    public long cancelApplication(long applyNum) {

        Apply apply = validateApply(applyNum);

        applyRepository.deleteById(applyNum);

        JobPosition position = jobPositionRepository.findById(apply.getJobPosition().getNum())
                .orElseThrow(()->
                {
                    CustomException ex = new CustomException(HttpErrorCode.CONFLICT,EnumResponseMessage.MESSAGE_NO_JOB_POSITION);
                    ex.addErrorDetail("applyNum",String.valueOf(applyNum));
                    ex.addErrorDetail("jobPostNum",String.valueOf(apply.getJobPosition().getNum()));
                    throw ex;
                }
        );

        position.decrementApplyCnt();
        jobPositionRepository.save(position);

        return userRepository.updateApplyStatus(apply.getUser().getUserNum(), false);

    }

    @Override
    @Transactional(readOnly = true)
    public ApplyInfoDTO getApplication(Long userNum) {
        if (!validateUser(userNum,false)) {
            return null;
        }
        return userRepository.findUserWithApplyAndJobPositionByUserNum(userNum);
    }

    private Apply validateApply(Long applyNum) {
        return applyRepository.findById(applyNum)
                .orElseThrow(() -> {
                            CustomException ex = new CustomException(HttpErrorCode.BAD_REQUEST,EnumResponseMessage.MESSAGE_NO_APPLY);
                            ex.addErrorDetail("applyNum",String.valueOf(applyNum));
                            return ex;
                        }
                );
        
    }

    private boolean validateJobPosition(Long jobPositionNum) {
        jobPositionRepository.findById(jobPositionNum)
                .orElseThrow(() -> {
                    CustomException ex = new CustomException(HttpErrorCode.BAD_REQUEST,EnumResponseMessage.MESSAGE_NO_JOB_POSITION);
                    ex.addErrorDetail("jobPostNum",String.valueOf(jobPositionNum));
                    return ex;
                }
        );
        return true;
    }

    private Boolean validateUser(Long userNum,boolean applyOrNot) {
        try{
            User userInfo = userRepository.findById(userNum).orElseThrow(()-> {
                CustomException ex = new CustomException(HttpErrorCode.BAD_REQUEST, EnumResponseMessage.MESSAGE_NO_USER);
                ex.addErrorDetail("userNum", String.valueOf(userNum));
                throw ex;
            });

            if(userInfo.getApplyStatus() && applyOrNot) {
                CustomException ex = new CustomException(HttpErrorCode.BAD_REQUEST, EnumResponseMessage.MESSAGE_ER_APPLY);
                ex.addErrorDetail("userId", String.valueOf(userInfo.getUserId()));
                ex.addErrorDetail("applyStatus", String.valueOf(userInfo.getApplyStatus()));
                throw ex;
            }
        }catch (CustomException e){
            throw e;
        }

        return true;
    }
}
