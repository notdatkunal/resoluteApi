package com.resolute.zero.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResetPasswordRequest {
    private String email;
    private Integer otp;
    private String newPassword;
}
