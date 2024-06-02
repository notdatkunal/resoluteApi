package com.resolute.zero.domains;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private String  fileName;
    @Column(nullable = false)
    private String  sequence;
    @Column(nullable = false)
    private String type;
}
