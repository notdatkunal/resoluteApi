package com.resolute.zero.domains;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL,targetEntity = CaseDetails.class)
    private  CaseDetails caseDetails = new CaseDetails();

    @OneToOne(cascade = CascadeType.ALL,targetEntity = CaseStatus.class)
    private CaseStatus caseStatus = new CaseStatus();
}
