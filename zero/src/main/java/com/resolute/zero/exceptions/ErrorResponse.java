package com.resolute.zero.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse extends RuntimeException{

    private int statusCode;
    private String message;

    public ErrorResponse(String message)
    {
        this.message = message;
    }
}
