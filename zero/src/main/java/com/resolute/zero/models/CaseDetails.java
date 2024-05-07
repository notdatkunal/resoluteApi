package com.resolute.zero.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CaseDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer caseId;
    @Column(nullable = false)
    private String caseType;
}
