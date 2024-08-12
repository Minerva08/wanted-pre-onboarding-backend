package com.example.preboarding.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="company")
@Getter
@ToString
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "company_seq_generator")
    @SequenceGenerator(name = "company_seq_generator", sequenceName = "company_sequence",
            initialValue = 1, allocationSize = 1)
    private Long comNum;
    @Column(unique = true)
    private String comId;
    private String comName;
    private String nation;
    private String region;
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyRole> companyRoleList = new ArrayList<>();

    @OneToMany(mappedBy = "company",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<JobPosition> jobPositionList = new ArrayList<>();
    @Builder
    public Company(Long comNum,String comId, String comName, String nation, String region) {
        if(comNum!=null) this.comNum=comNum;
        this.comId = comId;
        this.comName = comName;
        this.region = region;
        this.nation = nation;
    }

    public void addRole(Role role) {
        CompanyRole companyRole = CompanyRole.builder().company(this).role(role).build();
        this.companyRoleList.add(companyRole);
    }

}
