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
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bankId;

    private String bankName;
    private Date registrationDate;
    private String officerName;
    private String location;
    @Column(unique = true)
    private String username;

    // create relation between bank and case

}
