package com.example.preboarding.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class DateReq {
    @Schema(example = "2024-08-03 11:00:00", description="채용 공고 수정일")
    @Nullable
    private String updateDate;
    @NotEmpty(message = "Required")
    @Schema(example = "2024-08-01 29:00:00", description="채용 시작일")
    private String startDate;
    @Schema(example = "2024-08-20 23:00:00", description="채용 종료일")
    @NotEmpty(message = "Required")
    private String endDate;
}
