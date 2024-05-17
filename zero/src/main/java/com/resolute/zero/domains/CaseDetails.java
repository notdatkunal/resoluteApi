package com.resolute.zero.domains;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CaseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String caseType;

    @Column(nullable = false)
    private String claimAmountInSOC;
    private Date socFillingDate;
}
