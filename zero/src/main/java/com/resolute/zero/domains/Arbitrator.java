package com.resolute.zero.domains;

import jakarta.persistence.*;
import lombok.*;

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
    private Date registrationDate;
    private String location;
    @Column(unique = true)
    private String userName;


    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,targetEntity = BankCase.class)
    private List<BankCase> bankCaseList = new ArrayList<>();

}
