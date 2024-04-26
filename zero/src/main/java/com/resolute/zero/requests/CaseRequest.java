package com.resolute.zero.requests;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class CaseRequest {

    Date awardDate;
    Integer awardAmount;
    Date sec17AppFillingDate;
    Date sec17OrderDate;
    String sec17Status;
    String courtName;
    String arbitratorName;
    String lawyerName;
    String lmName;

}
