package com.example.preboarding.domain;


import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Transient;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class Date {
    @Transient
    private static final String formatter ="yyyy-MM-dd HH:mm:ss";
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Builder
    public Date(LocalDateTime createDate, LocalDateTime updateDate, LocalDateTime startDate, LocalDateTime endDate) {
        this.createDate = truncateToSecond(createDate);
        this.updateDate = truncateToSecond(updateDate);
        this.startDate = truncateToSecond(startDate);
        this.endDate = truncateToSecond(endDate);
    }

    private LocalDateTime truncateToSecond(LocalDateTime dateTime) {
        if (dateTime != null) {
            return dateTime.withNano(0);
        }
        return null;
    }

    @PrePersist
    @PreUpdate
    public void onSave() {
        this.createDate = truncateToSecond(this.createDate);
        this.updateDate = truncateToSecond(this.updateDate);
        this.startDate = truncateToSecond(this.startDate);
        this.endDate = truncateToSecond(this.endDate);
    }

    public String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.formatter);
        return (dateTime != null) ? dateTime.format(formatter) : null;
    }


    @Transient
    public String getFormattedCreateDate() {
        return formatDate(createDate);
    }

    @Transient
    public String getFormattedUpdateDate() {
        return formatDate(updateDate);
    }

    @Transient
    public String getFormattedStartDate() {
        return formatDate(startDate);
    }

    @Transient
    public String getFormattedEndDate() {
        return formatDate(endDate);
    }
}
