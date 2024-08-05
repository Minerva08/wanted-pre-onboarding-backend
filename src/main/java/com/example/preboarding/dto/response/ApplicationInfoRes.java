package com.example.preboarding.dto.response;

import com.example.preboarding.common.CommonResponse;
import com.example.preboarding.dto.ApplyInfoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ApplicationInfoRes extends CommonResponse {
    @NotNull
    @Schema(example = "1", description="지원자 번호")
    private Long userNum;
    @NotNull
    @Schema(example = "u_1", description="지원자 Id")
    private String userId;
    private ApplyInfoDTO applyInfo;
}
