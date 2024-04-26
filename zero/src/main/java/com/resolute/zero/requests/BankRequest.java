package com.resolute.zero.requests;


import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class BankRequest {

    String bankName;
    Date registrationDate;
    String officerName;
    String location;
    String username;
}
