package com.example.preboarding.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class JobPostAddReq extends JobPostReq{
    @NotNull(message = "not null")
    @Schema(example = "1", description="공고를 등록할 회사 번호")
    private Long companyNum;

}
