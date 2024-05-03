package com.resolute.zero.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Proceeding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer proceedingId;

    @Column(unique = true)
    private String proceedingTitle;


}
