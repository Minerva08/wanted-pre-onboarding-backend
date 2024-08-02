package com.example.preboarding.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userNum;
    private String userName;
    private String userId;
    private String userPw;
    private Boolean applyStatus;
    @OneToOne(mappedBy = "user")
    private Apply apply;

}
