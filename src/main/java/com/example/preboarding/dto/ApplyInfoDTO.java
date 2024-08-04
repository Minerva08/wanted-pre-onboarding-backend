package com.example.preboarding.dto;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
@ToString
@Getter
public class ApplyInfoDTO extends ApplyUserDTO {
    private String jobPositionTitle;
    private String jobPositionContent;
    private String skill;
    private String roleId;
    private String roleName;
    private String companyName;
    private String region;
    private String nation;
    private int reward;
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
        this.reward = reward;
        this.appliedDate = appliedDate;
    }
}
