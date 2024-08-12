package com.example.preboarding.service;

import com.example.preboarding.dto.request.JobPostReq;
import com.example.preboarding.dto.request.JobPostAddReq;
import com.example.preboarding.dto.response.JobPostInfoRes;
import org.springframework.data.domain.Pageable;

public interface JobPositionsService {

    Long registPosition(JobPostAddReq jobPosition);

    JobPostInfoRes searchJobPostionPost(String comName, String nation, String region, String[] roleArray, Pageable pageable);

    JobPostInfoRes getJobPostDetail(Long postNum);

    Long updateJobPostInfo(Long postNum, JobPostReq modJobPost);

    void deleteJobPost(Long postNum);
}
