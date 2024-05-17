package com.resolute.zero.domains;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer documentId;



    private String documentMainTypeTitle;
    private String documentSubTypeTitle;


    private String imageName;
//    @OneToOne(fetch = FetchType.LAZY)
//    private Image image;
}
