package com.resolute.zero.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserModel {

    String username;
    Boolean status;
    Integer id;
    String role;
}
