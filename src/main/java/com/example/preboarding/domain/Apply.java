package com.example.preboarding.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name="apply")
@Getter
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long applyNum;
    @OneToOne
    @JoinColumn(name="user_num")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_num")
    private JobPosition jobPosition;
}
