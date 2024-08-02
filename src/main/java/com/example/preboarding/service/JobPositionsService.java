package com.example.preboarding.service;

import com.example.preboarding.domain.JobPosition;
import org.springframework.stereotype.Service;

public interface JobPositionsService {

    Long registPosition(JobPosition jobPosition);
}
