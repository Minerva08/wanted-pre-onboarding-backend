package com.example.preboarding.service;

import com.example.preboarding.domain.JobPosition;
import com.example.preboarding.repository.jobPosition.JobPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobPositionServiceImpl implements JobPositionsService {
    @Autowired
    private JobPositionRepository jobPositionRepository;

    @Override
    public Long registPosition(JobPosition jobPosition) {
        return jobPositionRepository.save(jobPosition).getNum();
    }
}
