package com.example.preboarding.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="company_role")
@Getter
@NoArgsConstructor
public class CompanyRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_role_seq_generator")
    @SequenceGenerator(name = "company_role_seq_generator", sequenceName = "company_role_sequence",
            initialValue = 1, allocationSize = 1)
    private Long companyRoleNum;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="com_num")
    private Company company;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="role_num")
    private Role role;
    @OneToOne(mappedBy = "companyRole",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private JobPosition jobPosition;

    @Builder
    public CompanyRole(Long num,Company company, Role role) {
        if(num!=null)this.companyRoleNum = num;
        this.company = company;
        this.role = role;
    }
}
