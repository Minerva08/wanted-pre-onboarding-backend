package com.example.preboarding;

import com.example.preboarding.domain.Apply;
import com.example.preboarding.dto.ApplyInfoDTO;
import com.example.preboarding.repository.apply.ApplyRepository;
import com.example.preboarding.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
public class User {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplyRepository applyRepository;

    @Test
    @Transactional(readOnly = true)
    @DisplayName("지원한 공고 정보 조회")
    public void getApplyInfo(){
        Long userNum =2l;
        ApplyInfoDTO userWithApplyAndJobPositionByUserNum = userRepository.findUserWithApplyAndJobPositionByUserNum(userNum);
        if(userWithApplyAndJobPositionByUserNum!=null){
            System.out.println("appliedInfo : "+userWithApplyAndJobPositionByUserNum.toString());

        }
        System.out.println("지원한 채용 공고가 없습니다");
    }

    @Test
    @Transactional
    @DisplayName("채용 공고 지원 취소")
    @Rollback(value = false)
    public void cancelApply(){
        Long applyNum = 20l;
        applyRepository.findById(applyNum).ifPresentOrElse(
                apply -> {
                    applyRepository.delete(apply);
                    System.out.println("채용 공고 지원이 취소 되었습니다");
                    userRepository.updateApplyStatus(apply.getUser().getUserNum(),false);
                },
                ()->{
                    throw new NoSuchElementException("채용 공고 지원 내역이 없습니다.");
                }
        );


    }





}
