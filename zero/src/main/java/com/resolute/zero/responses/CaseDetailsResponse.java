package com.resolute.zero.responses;


import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class CaseDetailsResponse {

    String caseType;
    String caseNumber;
    Date fillingDate;
    String registrationNumber;
    Date registrationDate;


}
