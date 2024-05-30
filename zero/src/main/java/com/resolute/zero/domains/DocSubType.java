package com.resolute.zero.domains;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DocSubType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,unique = true)
    private String subType;
    @Column(nullable = false,unique = true)
    private String code;
    @OneToMany
    private Collection<CaseDocumentType> documentType;
}
