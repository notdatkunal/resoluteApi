package com.resolute.zero.requests;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class CaseSheetRequest {
    Integer bankId;
    Integer arbitratorId;
    String state;
    String caseNo;
    String type;
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
    String noticeDate;
    String refLetter;
    String socFillingDate;
    Double claimAmountInSOC;
    String firstHearingDate;
    String lastHearingDate;
    String nextHearingDate;
    String stagesOfLastHearingDate;
    String stagesOfNextHearingDate;
    String caseStatus;
    String flagForContested;
    String detailsRemark;
    String awardDate;
    String awardAmount;
    String sec17AppFillingDate;
    String sec17OrderDate;
    String sec17AppStatus;
    String courtName;
    String place;
    String arbitrator;
    String lawyerName;
    String lmName;
}
