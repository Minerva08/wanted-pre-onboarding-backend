package com.example.preboarding.dto.response;

import com.example.preboarding.common.CommonResponse;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class RegistRes extends CommonResponse {
    private Long registNum;
}
