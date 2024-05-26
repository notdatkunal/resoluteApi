package com.resolute.zero.domains;

import jakarta.persistence.*;
import lombok.*;
import org.w3c.dom.DocumentType;

import java.util.Collection;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DocMainType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,unique = true)
    private String mainType;
    @Column(nullable = false,unique = true)
    private String code;
    @OneToMany
    private Collection<CaseDocumentType> documentType;

}
