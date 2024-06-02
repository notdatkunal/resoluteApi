package com.resolute.zero.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler(value = AppException.class)
//    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ResponseEntity<?> handleException(AppException ex)
    {
        return  ex.getData();
    }
}
