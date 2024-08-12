package com.example.preboarding.dto.response;

import com.example.preboarding.common.CommonResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class UpdateStatusRes extends CommonResponse{
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long userNum;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long postNum;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long comNum;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long roleNum;

}
