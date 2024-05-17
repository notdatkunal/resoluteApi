package com.resolute.zero.domains;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Transient
    private Order section17 = new Order();
    @Transient
    private Order awardOrder = new Order();
    @OneToMany(fetch = FetchType.LAZY)
    private List<Order> other = new ArrayList<>();


}
