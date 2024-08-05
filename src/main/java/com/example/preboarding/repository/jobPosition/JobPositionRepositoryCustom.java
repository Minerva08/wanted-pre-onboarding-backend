package com.example.preboarding.repository.jobPosition;

import com.example.preboarding.domain.JobPosition;
import com.example.preboarding.dto.JobPostSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobPositionRepositoryCustom {
    List<JobPosition> findCompanyOtherPosition(Long comNum, Long postNum);
    Page<JobPosition> searchPosition(JobPostSearchDTO search);
}
