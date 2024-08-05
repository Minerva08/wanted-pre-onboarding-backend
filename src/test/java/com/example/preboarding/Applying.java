package com.example.preboarding;

import com.example.preboarding.domain.Apply;
import com.example.preboarding.domain.JobPosition;
import com.example.preboarding.domain.User;
import com.example.preboarding.dto.ApplyUserDTO;
import com.example.preboarding.repository.apply.ApplyRepository;
import com.example.preboarding.repository.jobPosition.JobPositionRepository;
import com.example.preboarding.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class Applying {

    @Autowired
    private ApplyRepository applyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobPositionRepository jobPositionRepository;

    @Test
    @DisplayName("채용 공고 지원")
    @Transactional
    @Rollback(value = false)
    public void applyingPosition() throws Exception {

        User user = userRepository.findById(1l)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID"));

        if(user.getApplyStatus()){
            throw new Exception("이미 지원한 지원자 입니다");
        }
        Long applyPostNum = 20l;
        JobPosition jobPosition = jobPositionRepository.findById(applyPostNum)
                .orElseThrow(() -> new NoSuchElementException("JobPosition not found with ID"));

        Apply apply = Apply.builder()
                .user(user)
                .jobPosition(jobPosition)
                .applyDate(LocalDateTime.now())
                .build();

        Apply savedApply = null;
        try {
            savedApply = applyRepository.save(apply);
            System.out.println("Save successful: " + savedApply);
        } catch (DataIntegrityViolationException e) {
            System.out.println("DataIntegrityViolationException 발생: " + e.getMessage());
            System.out.println("삽입 시도한 Apply: " + apply);
        } catch (Exception e) {
            System.out.println("기타 예외 발생: " + e.getMessage());
            e.printStackTrace();
        }
        assertEquals(applyPostNum,savedApply.getJobPosition().getNum(),"공고 지원 내역 불일치");

        int beforeApplyCnt = jobPosition.getApplyCnt();
        jobPosition.incrementApplyCnt();
        jobPositionRepository.save(jobPosition);

        jobPositionRepository.findById(applyPostNum);
        assertNotEquals(beforeApplyCnt,jobPosition.getApplyCnt(),"지원 횟수가 증가 되지 않았습니다");

        Long userNum = userRepository.updateApplyStatus(user.getUserNum(), true);
        assertEquals(userNum,user.getUserNum(),"사용자의 지원 상태가 변경 되지 않았습니다.");

    }

    @Test
    @DisplayName("채용 공고 지원자 목록 조회")
    @Transactional
    @Rollback(value = false)
    public void getApplyListByJobPosition(){
        Long applyPostNum = 23l;

        List<ApplyUserDTO> applies = applyRepository.findByApplyNum(applyPostNum);

        assertThat(applies).isNotEmpty();
        applies.stream().forEach(
                e -> {
                    System.out.println("applyNum : "+e.getApplyNum());
                    System.out.println("userName : "+e.getUserName());
                    System.out.println("userId : "+e.getUserId());
                }
        );

    }


}
