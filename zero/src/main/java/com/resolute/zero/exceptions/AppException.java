package com.resolute.zero.exceptions;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Data
@Builder
@EqualsAndHashCode(callSuper=false)
public class AppException extends RuntimeException {
    private ResponseEntity<?> data;
}
