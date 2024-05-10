package com.resolute.zero.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {
    String username;
    String password;
    String role;
}
