package com.example.preboarding.dto;

import com.example.preboarding.domain.Date;
import com.example.preboarding.domain.JobPosition;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SummaryJobPost {
    private Long postNum;
    private String postTitle;
    private String comId;
    private String comName;
    private String roleId;
    private String roleName;
    private Date date;

    @Builder
    public SummaryJobPost(Long postNum, String postTitle, String comId, String comName, String roleId, String roleName, Date date) {
        this.postNum = postNum;
        this.postTitle = postTitle;
        this.comId = comId;
        this.comName = comName;
        this.roleId = roleId;
        this.roleName = roleName;
        this.date = date;
    }

    public SummaryJobPost(JobPosition jobPosition) {
        this.postNum = jobPosition.getNum();
        this.postTitle = jobPosition.getPostTitle();
        this.comId = jobPosition.getCompany().getComId();
        this.comName = jobPosition.getCompany().getComName();
        this.roleId = jobPosition.getCompanyRole().getRole().getRoleId();
        this.roleName = jobPosition.getCompanyRole().getRole().getRoleName();
        this.date = jobPosition.getDate();
    }
}
