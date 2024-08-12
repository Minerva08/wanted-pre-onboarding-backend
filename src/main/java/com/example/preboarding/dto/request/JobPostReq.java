package com.example.preboarding.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@NotNull
@Getter
public class JobPostReq {
    @NotEmpty(message = "Required")
    @Schema(example = "원티드 백엔드 서버 개발자 모집 공고", description="요청할 채용 공고 제목")
    private String postTitle;
    @NotNull(message = "not null")
    @Schema(example = "1", description="요청할 직무 번호")
    private Long roleNum;
    @NotNull(message = "not null")
    @Schema(example = "1년 이상의 백앤드 개발 경험", description="요청할 채용 공고 내용")
    private String contents;
    @Schema(example = "500,000", description="채용 보상금")
    private Integer reward;
    @Schema(example = "java, MySQL", description="요청할 채용 공고 내용")
    private String skill;
    @NotNull(message = "not null")
    private DateReq date;
}
