package com.resolute.zero.requests;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class ArbitratorRequest {

    String arbitratorName;
    Date registrationDate;
    String location;
    String userName;
    String email;
}
