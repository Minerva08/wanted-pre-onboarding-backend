package com.example.preboarding.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "2024-07-31 09:00:00", description="채용 공고 등록일")
    private LocalDateTime createDate;
    @Schema(example = "2024-08-03 11:00:00", description="채용 공고 수정일")
    private LocalDateTime updateDate;
    @Schema(example = "2024-08-01 29:00:00", description="채용 시작일")
    private LocalDateTime startDate;
    @Schema(example = "2024-08-20 23:00:00", description="채용 종료일")
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
    @JsonIgnore
    public String getFormattedCreateDate() {
        return formatDate(createDate);
    }

    @Transient
    @JsonIgnore
    public String getFormattedUpdateDate() {
        return formatDate(updateDate);
    }

    @Transient
    @JsonIgnore
    public String getFormattedStartDate() {
        return formatDate(startDate);
    }

    @Transient
    @JsonIgnore
    public String getFormattedEndDate() {
        return formatDate(endDate);
    }
}
