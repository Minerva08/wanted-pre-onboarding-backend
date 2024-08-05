package com.example.preboarding.service;

import com.example.preboarding.domain.JobPosition;
import com.example.preboarding.dto.request.JobPositionPostReq;
import com.example.preboarding.dto.request.JobPostionAddReq;
import com.example.preboarding.dto.response.JobPositionPostRes;
import com.example.preboarding.dto.response.JobPostInfoRes;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface JobPositionsService {

    Long registPosition(JobPostionAddReq jobPosition);

    JobPostInfoRes searchJobPostionPost(String comName, String nation, String region, String[] roleArray, Pageable pageable);

    JobPostInfoRes getJobPostDetail(Long postNum);

    Long updateJobPostInfo(Long postNum, JobPositionPostReq modJobPost);

    void deleteJobPost(Long postNum);
}
