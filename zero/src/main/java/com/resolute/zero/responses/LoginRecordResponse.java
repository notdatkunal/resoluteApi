package com.resolute.zero.responses;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class LoginRecordResponse {
    String username;
    String ip;
    String country;
    Instant loginTime;
}
