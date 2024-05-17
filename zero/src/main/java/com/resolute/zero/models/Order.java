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
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;


    private Date date;
    private Boolean awardOrder = false;
    private Boolean section17 = false;
    private String  fileName;


}
