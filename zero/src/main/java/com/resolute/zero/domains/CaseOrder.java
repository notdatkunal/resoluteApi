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
public class CaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private Date date;
    @Column(nullable = false)
    private Boolean awardOrder = false;
    @Column(nullable = false)
    private Boolean section17 = false;
    private String  fileName;


}
