package com.resolute.zero.models;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(unique = true)
    private String arbitratorName;


}
