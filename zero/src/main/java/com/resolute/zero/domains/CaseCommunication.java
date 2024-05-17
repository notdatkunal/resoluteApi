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
public class CaseCommunication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commId;


    private Date date;
    @OneToMany
    private List<Image> imageList = new ArrayList<>();


}
