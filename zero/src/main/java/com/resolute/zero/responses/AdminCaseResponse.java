package com.resolute.zero.responses;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class AdminCaseResponse {
    Integer id;
    Integer bankId;
    Date sec17OrderDate;
    Integer arbitratorId;
    String state;
    String caseType;
    String caseNo;
    String zone;
    String branchName;
    String customerId;
    String accountNumber;
    String creditCardNumber;
    String customerName;
    String actualProduct;
    String flagProductGroup;
    String natureOfLegalAction;
    Double totalTos;
    Double totalTosInCr;
    Date noticeDate;
    Date refLetter;
    Date socFillingDate;
    Double claimAmountInSOC;
    Date firstHearingDate;
    Date lastHearingDate;
    Date nextHearingDate;
    String stagesOfLastHearingDate;
    String stagesOfNextHearingDate;
    String caseStatus;
    String flagForContested;
    String detailsRemark;
    Date awardDate;
    String awardAmount;
    Date sec17AppFillingDate;
    String sec17AppStatus;
    String courtName;
    String place;
    String arbitrator;
    String lawyerName;
    String lmName;
    Date registrationDate;
}
