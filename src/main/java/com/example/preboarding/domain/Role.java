package com.example.preboarding.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="role")
@Getter
@ToString
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long roleNum;
    private String roleId;
    private String roleName;
    @OneToMany(mappedBy = "role")
    private List<CompanyRole> companyRoleList = new ArrayList<>();

    public Role() {
    }

    @Builder
    public Role(Long roleNum, String roleId, String roleName) {
        if(roleNum!=null) this.roleNum = roleNum;
        this.roleId = roleId;
        this.roleName = roleName;
    }

}
