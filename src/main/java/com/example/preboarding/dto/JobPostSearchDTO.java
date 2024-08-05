package com.example.preboarding.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import org.springframework.data.domain.Pageable;


@Builder
@Getter
public class JobPostSearchDTO {
    private String comName;
    private String nation;
    private String region;
    private List<Long> roleNumList;
    private Pageable pageable;

}
