package com.example.preboarding.repository;

import com.example.preboarding.domain.JobPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobPositionRepositoryCustom {
    JobPosition findByIdDetail(Long jobPositionNum);

    List<JobPosition> findCompanyOtherPosition(Long comNum, Long postNum);

    Page<JobPosition> searchPosition(String comName,String region,String nation, List<Long> roleNums, Pageable pageable);

}
