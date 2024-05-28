package com.resolute.zero.controllers;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class LoginRecordResponse {
    String username;
    Instant loginTime;
}
