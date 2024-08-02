package com.example.preboarding.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name="company_role")
@Getter
public class CompanyRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long companyRoleNum;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="com_num")
    private Company company;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_num")
    private Role role;
//    @OneToOne(mappedBy = "companyRole")
//    private JobPosition jobPosition;

    public CompanyRole() {
    }

    @Builder
    public CompanyRole(Long num,Company company, Role role) {
        if(num!=null)this.companyRoleNum = num;
        this.company = company;
        this.role = role;
    }
}
