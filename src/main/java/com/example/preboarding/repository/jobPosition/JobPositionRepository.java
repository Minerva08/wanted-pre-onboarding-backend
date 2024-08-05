package com.example.preboarding.repository.jobPosition;

import com.example.preboarding.domain.JobPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition,Long>, JobPositionRepositoryCustom{
    List<JobPosition> findCompanyOtherPosition(Long comNum, Long postNum);


}
