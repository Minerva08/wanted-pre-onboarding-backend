package com.example.preboarding.dto.response;

import com.example.preboarding.domain.*;
import com.example.preboarding.dto.SummaryJobPost;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Getter
@Setter
public class JobPostInfoRes extends JobPositionPostRes {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer pageNum;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<JobPost> jobPostList;
    private JobPostDetail detail;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SummaryJobPost> otherPostList;

    @Getter
    @Setter
    public static class JobPost {
        private Long postNum;
        private String postTitle;
        private Long roleNum;
        private String roleId;
        private String roleName;
        private Long comNum;
        private String comId;
        private String comName;
        private int reward;
        private int applyCnt;
        private String skill;
        private Date date;
        @Builder
        public JobPost(JobPosition jobPosition) {
            this.postNum = jobPosition.getNum();
            this.postTitle = jobPosition.getPostTitle();
            this.roleId = jobPosition.getCompanyRole().getRole().getRoleId(); // Assume CompanyRole can be used as Role
            this.roleNum = jobPosition.getCompanyRole().getRole().getRoleNum(); // Assume CompanyRole can be used as Role
            this.roleName = jobPosition.getCompanyRole().getRole().getRoleName(); // Assume CompanyRole can be used as Role
            this.comNum = jobPosition.getCompany().getComNum();
            this.comId = jobPosition.getCompany().getComId();
            this.comName = jobPosition.getCompany().getComName();
            this.reward = jobPosition.getReward();
            this.applyCnt = jobPosition.getApplyCnt();
            this.skill = jobPosition.getSkill();
            this.date = jobPosition.getDate();
        }

    }

    @Getter
    @Setter
    public static class JobPostDetail {
        private Long postNum;
        private String postTitle;
        private Long roleNum;
        private String roleId;
        private String roleName;
        private Long comNum;
        private String comId;
        private String comName;
        private String contents;
        private int reward;
        private int applyCnt;
        private String skill;
        private Date date;
        @Builder
        public JobPostDetail(JobPosition jobPosition) {
            this.postNum = jobPosition.getNum();
            this.postTitle = jobPosition.getPostTitle();
            this.roleId = jobPosition.getCompanyRole().getRole().getRoleId(); // Assume CompanyRole can be used as Role
            this.roleNum = jobPosition.getCompanyRole().getRole().getRoleNum(); // Assume CompanyRole can be used as Role
            this.roleName = jobPosition.getCompanyRole().getRole().getRoleName(); // Assume CompanyRole can be used as Role
            this.comNum = jobPosition.getCompany().getComNum();
            this.comId = jobPosition.getCompany().getComId();
            this.comName = jobPosition.getCompany().getComName();
            this.contents = jobPosition.getContents();
            this.reward = jobPosition.getReward();
            this.applyCnt = jobPosition.getApplyCnt();
            this.skill = jobPosition.getSkill();
            this.date = jobPosition.getDate();
        }

    }

}
