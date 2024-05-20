package com.resolute.zero.responses;


import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class BankResponse {
    Integer serialNo;
    Integer bankId;
    String bankName;
    String username;
    Date registrationDate;
}
