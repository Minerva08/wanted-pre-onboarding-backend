package com.example.preboarding.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="company")
@Getter
@ToString
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long comNum;
    private String comId;
    private String comName;
    private String nation;
    private String region;
    @OneToMany(mappedBy = "company")
    private List<CompanyRole> companyRoleList = new ArrayList<>();

    @OneToMany(mappedBy = "company")
    private List<JobPosition> jobPositionList = new ArrayList<>();

    public Company() {
    }
    @Builder
    public Company(Long comNum,String comId, String comName, String nation, String region) {
        if(comNum!=null) this.comNum=comNum;
        this.comId = comId;
        this.comName = comName;
        this.region = region;
        this.nation = nation;
    }
}
