package com.resolute.zero.exceptions;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Data
@Builder
public class AppException extends RuntimeException {
    private ResponseEntity<?> data;
}
