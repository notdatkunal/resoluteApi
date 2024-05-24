package com.resolute.zero.domains;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Arbitrator {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String arbitratorName;
    @CreationTimestamp
    private Instant registrationDate;

    private String location;
    @Column(unique = true)
    private String userName;
    String email;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,targetEntity = BankCase.class)
    private List<BankCase> bankCaseList = new ArrayList<>();

}
