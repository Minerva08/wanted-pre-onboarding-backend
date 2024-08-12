package com.example.preboarding.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_seq_generator")
    @SequenceGenerator(name = "user_seq_generator", sequenceName = "user_sequence",
            initialValue = 1, allocationSize = 1)
    private Long userNum;
    private String userName;
    @Column(unique = true)
    private String userId;
    private String userPw;
    private Boolean applyStatus;
    @OneToOne(mappedBy = "user",fetch = FetchType.LAZY, cascade ={ CascadeType.REMOVE,CascadeType.MERGE}, orphanRemoval = true)
    private Apply apply;

    @Builder
    public User(String userName, String userId, String userPw) {
        this.userName = userName;
        this.userId = userId;
        this.userPw = userPw;
    }


}
