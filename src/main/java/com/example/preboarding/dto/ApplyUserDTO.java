package com.example.preboarding.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApplyUserDTO {
    @Schema(example = "1", description="지원 번호")
    private Long applyNum;
    @Schema(example = "1", description="지원자 Id")
    private String userId;
    @Schema(example = "1", description="지원자 이름")
    private String userName;
    @Schema(example = "1", description="지원한 채용 공고 번호")
    private Long jobPostNum;

    public ApplyUserDTO(Long applyNum, String userId, String userName, Long jobPostNum) {
        this.applyNum = applyNum;
        this.userId = userId;
        this.userName = userName;
        this.jobPostNum = jobPostNum;
    }
}
