package com.example.preboarding.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="job_positioning")
@Getter
@NoArgsConstructor
public class JobPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_post_seq_generator")
    @SequenceGenerator(name = "job_post_seq_generator", sequenceName = "job_post_sequence",
            initialValue = 1, allocationSize = 1)
    @Column(name="post_num")
    private Long num;
    private String postTitle;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="company_role_num",nullable = false)
    private CompanyRole companyRole;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="com_num")
    private Company company;
    private String contents;
    private int reward;
    private int applyCnt;
    private String skill;
    private Date date;
    @OneToMany(mappedBy = "jobPosition",cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Apply> applyList = new ArrayList<>();
    @Builder
    public JobPosition(Long postNum, String postTitle, CompanyRole companyRole, String contents, String skill,int applyCnt, int reward, Date date) {
        if(postNum!=null) this.num = postNum;
        this.postTitle = postTitle;
        this.companyRole = companyRole;
        this.contents = contents;
        this.company = companyRole.getCompany();
        this.reward = reward;
        this.date=date;
        this.skill=skill;
        if(applyCnt!=0) this.applyCnt=applyCnt;
    }

    public void incrementApplyCnt() {
        this.applyCnt++;
    }


}
