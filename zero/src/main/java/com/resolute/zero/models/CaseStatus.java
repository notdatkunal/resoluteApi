package com.resolute.zero.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CaseStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statusId;
    @Column(nullable = false)
    private Date firstHearingDate;
    @Column(nullable = false)
    private Date lastHearingDate;
    @Column(nullable = false)
    private String courtNumber;
    @Column(nullable = false)
    private String judgeNumber;
}