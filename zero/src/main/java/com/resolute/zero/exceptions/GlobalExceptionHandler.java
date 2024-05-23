//package com.resolute.zero.exceptions;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//@ControllerAdvice
//public class GlobalExceptionHandler  {
//
//    @ExceptionHandler(value = LogoutException.class)
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public @ResponseBody ErrorResponse handleException(LogoutException ex)
//    {
//        return new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
//    }
//}
