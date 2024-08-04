package com.example.preboarding.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name="apply")
@Getter
@NoArgsConstructor
@ToString
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "apply_seq_generator")
    @SequenceGenerator(name = "apply_seq_generator", sequenceName = "apply_sequence",
            initialValue = 1, allocationSize = 1)
    private Long applyNum;
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST,orphanRemoval = true)
    @JoinColumn(name="user_num")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name="post_num")
    private JobPosition jobPosition;
    private LocalDateTime applyDate;

    @Builder
    public Apply(User user, JobPosition jobPosition,LocalDateTime applyDate) {
        this.user = user;
        this.jobPosition = jobPosition;
        this.applyDate = applyDate;
    }



}
