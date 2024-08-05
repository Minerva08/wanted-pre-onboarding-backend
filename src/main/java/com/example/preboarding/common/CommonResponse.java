package com.example.preboarding.common;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class CommonResponse {
    @NotNull
    @NotBlank
    @Schema(description = "httpStatus" , example = "200", defaultValue = "200")
    private Integer status;

    @NotNull
    @NotBlank
    @Schema(description = "message" , example = "요청 처리 정상", defaultValue = "요청 처리 정상")
    private String message;

}
