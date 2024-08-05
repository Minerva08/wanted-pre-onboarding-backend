package com.example.preboarding.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ApplicationReq {
    @NotNull(message = "not be null")
    @Schema(example = "1", description="지원할 채용 공고 번호")
    private Long jobPositionNum;
    @NotNull(message = "not be null")
    @Schema(example = "1",description = "지원자 번호")
    private Long userNum;
}
