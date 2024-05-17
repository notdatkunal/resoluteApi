package com.resolute.zero.requests;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class AdminCaseRequest {
    String state;
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
    String totalTos;
    String totalTosInCr;
    Date noticeDate;
    Date refLetter;

    Date socFillingDate;
    String claimAmountInSOC;


    Date firstHearingDate;
    Date lastHearingDate;


    Date stagesOfLastHearingDate;
    Date nextHearingDate;
    Date stagesOfNextHearingDate;
    String caseStatus;
    String flagForContested;
    String detailsRemark;
    Date awardDate;

    String awardAmount;
    Date sec17AppFillingDate;
    Date sec17AppStatus;
    String courtName;
    String place;
    String arbitrator;
    String lawyerName;
    String lmName;
}
