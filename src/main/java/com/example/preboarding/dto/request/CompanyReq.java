package com.example.preboarding.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CompanyReq {
    @NotNull
    private String comId;
    @NotNull
    private String comName;
    @NotNull
    private String nation;
    @NotNull
    private String region;
}
