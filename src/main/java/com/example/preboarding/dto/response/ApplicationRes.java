package com.example.preboarding.dto.response;

import com.example.preboarding.common.CommonResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ApplicationRes extends CommonResponse {
    @NotNull
    @Schema(description = "지원 번호" , example = "1")
    private Long applyNum;
    @NotNull
    @Schema(description = "지원한 채용 공고 번호" , example = "1")
    private Long jobPostNum;
    @NotNull
    @Schema(description = "지원자 번호" , example = "1")
    private Long userNum;
    @NotNull
    @Schema(description = "지원자 ID" , example = "1")
    private String userId;
    @Schema(description = "지원 여부 상태" , example = "true")
    private boolean applyStatus;

}
