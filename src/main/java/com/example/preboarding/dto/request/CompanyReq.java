package com.example.preboarding.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CompanyReq {
    @NotNull
    @Schema(example = "wanted", description="회사 Id")
    private String comId;
    @NotNull
    @Schema(example = "원티드", description="회사명")
    private String comName;
    @NotNull
    @Schema(example = "한국", description="국가")
    private String nation;
    @NotNull
    @Schema(example = "서울", description="지역")
    private String region;
}
