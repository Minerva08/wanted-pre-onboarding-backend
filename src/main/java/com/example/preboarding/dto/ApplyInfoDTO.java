package com.example.preboarding.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ToString
@Getter
@Setter
public class ApplyInfoDTO extends ApplyUserDTO {
    @Schema(example = "원티드 백엔드 서버 개발자 모집 공고", description="지원한 채용 공고 제목")
    private String jobPositionTitle;
    @Schema(example = "백엔드 업무 경험 1년 이상", description="공고 내용")
    private String jobPositionContent;
    @Schema(example = "java,MySQL", description="스킬")
    private String skill;
    @Schema(example = "back-end", description="직무Id")
    private String roleId;
    @Schema(example = "백엔드", description="직무명")
    private String roleName;
    @Schema(example = "1", description="회사번호")
    private String companyNum;
    @Schema(example = "wanted", description="회사Id")
    private String companyId;
    @Schema(example = "원티드", description="회사명")
    private String companyName;
    @Schema(example = "서울", description="지역")
    private String region;
    @Schema(example = "한국", description="국가")
    private String nation;
    @Schema(example = "500,000", description="채용 보상금")
    private String reward;
    @Schema(example = "2024-08-01 09:00:00", description="지원할 채용 공고 번호")
    private LocalDateTime appliedDate;


    public ApplyInfoDTO(Long applyNum, String userId, String userName, Long jobPostNum,
                        String jobPositionTitle, String jobPositionContent, String skill,
                        String roleId, String roleName, String companyName,
                        String region, String nation,
                        int reward, LocalDateTime appliedDate) {
        super(applyNum, userId, userName, jobPostNum);
        this.jobPositionTitle = jobPositionTitle;
        this.jobPositionContent = jobPositionContent;
        this.skill = skill;
        this.roleId = roleId;
        this.roleName = roleName;
        this.companyName = companyName;
        this.region = region;
        this.nation = nation;
        this.reward = NumberFormat.getInstance().format(reward);
        this.appliedDate = appliedDate;
    }

}
