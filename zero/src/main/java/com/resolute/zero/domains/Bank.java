package com.resolute.zero.domains;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String bankName;
    private Date registrationDate;
    private String officerName;
    private String location;
    @Column(unique = true)
    private String username;


    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;

    // create relation between bank and case

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,targetEntity = BankCase.class)
    private List<BankCase> bankCaseList = new ArrayList<>();

}
