package com.resolute.zero.models;

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
    private Integer caseId;

    @OneToOne(cascade = CascadeType.ALL)
    private final CaseDetails caseDetails = new CaseDetails();
}
