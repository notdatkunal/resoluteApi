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
    private List<Proceeding> proceeding = new ArrayList<Proceeding>();


    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,targetEntity = CaseOrder.class)
    private List<CaseOrder> orders = new ArrayList<>();

    @ManyToOne
    private Arbitrator arbitrator = new Arbitrator();

    @ManyToOne
    private Bank bank = new Bank();


    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String zone;
    @Column(nullable = false)
    private String branchName;
    @Column(nullable = false)
    private String customerId;
    @Column(nullable = false)
    private String accountNumber;
    @Column(nullable = false)
    private String creditCardNumber;
    @Column(nullable = false)
    private String customerName;
    @Column(nullable = false)
    private String actualProduct;
    @Column(nullable = false)
    private String flagProductGroup;
    @Column(nullable = false)
    private String natureOfLegalAction;
    @Column(nullable = false)
    private String totalTos;
    @Column(nullable = false)
    private String totalTosInCr;
    @Column(nullable = false)
    private Date noticeDate;
    @Column(nullable = false)
    private Date refLetter;

    @Column(nullable = false)
    private Date stagesOfLastHearingDate;
    @Column(nullable = false)
    private Date nextHearingDate;
    @Column(nullable = false)
    private Date stagesOfNextHearingDate;
    @Column(nullable = false)
    private String caseStatus;
    @Column(nullable = false)
    private String flagForContested;
    @Column(nullable = false)
    private String detailsRemark;
    @Column(nullable = false)
    private Date awardDate;
    @Column(nullable = false)
    private String awardAmount;
    @Column(nullable = false)
    private Date sec17AppFillingDate;
    @Column(nullable = false)
    private Date sec17AppStatus;
    @Column(nullable = false)
    private String courtName;
    @Column(nullable = false)
    private String place;


    @Column(nullable = false)
    private String lawyerName;
    @Column(nullable = false)
    private String lmName;

}
