package com.resolute.zero.responses;


import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class ArbitratorResponse {
    Integer serialNo;
    Integer arbitratorId;
    String arbitratorName;
    String email;
    String username;
    Date registrationDate;
    String location;
}
