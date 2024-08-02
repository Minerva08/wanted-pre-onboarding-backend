package com.example.preboarding.domain;


import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Embeddable
@Getter
@Setter
public class Date {
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Date() {
    }

}
