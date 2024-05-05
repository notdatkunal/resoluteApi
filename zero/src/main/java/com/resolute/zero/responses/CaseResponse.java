package com.resolute.zero.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Data
public class CaseResponse {
    Integer serialNo;
    Integer caseNo;
    String  caseType;
    Date fillingDate;
    Integer registrationNumber;
    Date registrationDate;
}
