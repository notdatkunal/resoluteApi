package com.resolute.zero.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.resolute.zero.helpers.TemplateType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;
    private String template;
    @Column(unique = true)
    private TemplateType type;
}
