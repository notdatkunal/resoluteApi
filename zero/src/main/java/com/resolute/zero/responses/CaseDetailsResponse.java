package com.resolute.zero.responses;


import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class CaseDetailsResponse {

    String caseType;
    String fillingNumber;
    Date fillingDate;
    String registrationNumber;
    Date registrationDate;
    String CNRNumber;

}
