package com.resolute.zero.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Builder
@Data
public class CaseResponse {
    Integer id;
    String caseNo;
    String  caseType;
    Date registrationDate;
    Date fillingDate;
    String accountNumber;
}
