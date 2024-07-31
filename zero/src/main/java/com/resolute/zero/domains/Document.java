package com.resolute.zero.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String documentMainTypeTitle;
    private String documentSubTypeTitle;
    private String fileExtension;
    private Integer caseId;

    @CreationTimestamp
    @JsonIgnore
    private Instant createdAt;
    @UpdateTimestamp
    @JsonIgnore
    private Instant updatedAt;

    @Column(unique = true)
    private String imageName;

}
