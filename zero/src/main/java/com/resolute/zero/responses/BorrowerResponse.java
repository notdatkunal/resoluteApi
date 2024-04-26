package com.resolute.zero.responses;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class BorrowerResponse {

    Integer serialNo;
    Integer borrowerId;
    String borrowerName;
    Date registrationDate;
}
