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
public class Arbitrator {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer arbitratorId;

    private String arbitratorName;
    private Date registrationDate;
    private String location;
    @Column(unique = true)
    private String userName;


}
