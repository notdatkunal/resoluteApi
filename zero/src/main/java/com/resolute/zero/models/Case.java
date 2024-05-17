package com.resolute.zero.models;

import com.resolute.zero.requests.AdminCaseRequest;
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
public class Case {


    public static Case createCase(AdminCaseRequest req){
        var caseObj = new Case();

        caseObj.setCaseNo(req.getCaseNo());
        caseObj.setState(req.getState());
        caseObj.setZone(req.getZone());
        caseObj.setBranchName(req.getBranchName());
        caseObj.setCustomerId(req.getCustomerId());
        caseObj.setAccountNumber(req.getAccountNumber());
        caseObj.setCreditCardNumber(req.getCreditCardNumber());
        caseObj.setCustomerName(req.getCustomerName());
        caseObj.setActualProduct(req.getActualProduct());
        caseObj.setFlagProductGroup(req.getFlagProductGroup());
        caseObj.setNatureOfLegalAction(req.getNatureOfLegalAction());
        caseObj.setTotalTos(req.getTotalTos());
        caseObj.setTotalTosInCr(req.getTotalTosInCr());
        caseObj.setNoticeDate(req.getNoticeDate());
        caseObj.setRefLetter(req.getRefLetter());
        caseObj.caseHistory.getCaseDetails().setSocFillingDate(req.getSocFillingDate());
        caseObj.caseHistory.getCaseDetails().setClaimAmountInSOC(req.getClaimAmountInSOC());
        caseObj.caseHistory.getCaseStatus().setFirstHearingDate(req.getFirstHearingDate());
        caseObj.caseHistory.getCaseStatus().setLastHearingDate(req.getLastHearingDate());
        caseObj.setLmName(req.getLmName());
        caseObj.setLawyerName(req.getLawyerName());

        caseObj.arbitrator.setUserName(req.getArbitrator());


        caseObj.setPlace(req.getPlace());
        caseObj.setCourtName(req.getCourtName());
        caseObj.setSec17AppStatus(req.getSec17AppStatus());
        caseObj.setSec17AppFillingDate(req.getSec17AppFillingDate());
        caseObj.setAwardAmount(req.getAwardAmount());
        caseObj.setAwardDate(req.getAwardDate());
        caseObj.setDetailsRemark(req.getDetailsRemark());
        caseObj.setFlagForContested(req.getFlagForContested());
        caseObj.setCaseStatus(req.getCaseStatus());
        caseObj.setStagesOfLastHearingDate(req.getStagesOfLastHearingDate());
        caseObj.setNextHearingDate(req.getNextHearingDate());
        caseObj.setStagesOfNextHearingDate(req.getStagesOfNextHearingDate());

        return caseObj;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer caseId;
    @Column(nullable = false)
    private String caseNo;


    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;


    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private CaseHistory caseHistory = new CaseHistory();
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private CaseDocument caseDocuments = new CaseDocument();
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Proceeding> caseProceedings = new ArrayList<Proceeding>();

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private CaseOrder caseOrder = new CaseOrder();
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<CaseCommunication> caseCommunication = new ArrayList<>();





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
   @ManyToOne
   private Arbitrator arbitrator = new Arbitrator();

    @Column(nullable = false)
    private String lawyerName;
    @Column(nullable = false)
    private String lmName;

}
