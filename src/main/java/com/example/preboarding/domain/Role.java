package com.example.preboarding.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="role")
@Getter
@ToString
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "role_seq_generator")
    @SequenceGenerator(name = "role_seq_generator", sequenceName = "role_sequence",
            initialValue = 1, allocationSize = 1)
    private Long roleNum;
    private String roleId;
    private String roleName;
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyRole> companyRoleList = new ArrayList<>();

    @Builder
    public Role(Long roleNum, String roleId, String roleName) {
        if(roleNum!=null) this.roleNum = roleNum;
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public void addCompany(Company company) {
        CompanyRole companyRole = CompanyRole.builder().company(company).role(this).build();
        this.companyRoleList.add(companyRole);
    }


}
