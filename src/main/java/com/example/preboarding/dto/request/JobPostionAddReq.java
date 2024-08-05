package com.example.preboarding.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class JobPostionAddReq extends JobPositionPostReq{
    @NotNull(message = "not null")
    @Schema(example = "1", description="요청할 회사 번호")
    private Long companyNum;

}
