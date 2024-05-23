package com.resolute.zero.domains;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BankCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String caseNo;


    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;


    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private CaseHistory caseHistory = new CaseHistory();


    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Document> documentList = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Proceeding> proceeding = new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,targetEntity = CaseOrder.class)
    private List<CaseOrder> orders = new ArrayList<>();

    @ManyToOne
    private Arbitrator arbitrator = new Arbitrator();

    @ManyToOne
    private Bank bank = new Bank();


    private String state;
    private String zone;
    private String branchName;
    @Column(nullable = false)
    private String customerId;
    @Column(nullable = false)
    private String accountNumber;
    private String creditCardNumber;
    @Column(nullable = false)
    private String customerName;
    private String actualProduct;
    private String flagProductGroup;
    private String natureOfLegalAction;
    private Double totalTos;
    private Double totalTosInCr;
    private Date noticeDate;
    private Date refLetter;
    private String stagesOfLastHearingDate;
    private Date nextHearingDate;
    private String stagesOfNextHearingDate;

    private String caseStatus;

    private String flagForContested;

    private String detailsRemark;

    private Date awardDate;

    private String awardAmount;

    private Date sec17AppFillingDate;

    private Date sec17AppStatus;

    private String courtName;

    private String place;



    private String lawyerName;

    private String lmName;

}
