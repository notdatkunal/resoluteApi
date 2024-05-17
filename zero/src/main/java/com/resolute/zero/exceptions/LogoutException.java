package com.resolute.zero.exceptions;


import lombok.*;
import lombok.experimental.StandardException;


@Getter
@Setter
@ToString
@Builder
@StandardException
@AllArgsConstructor
public class LogoutException extends RuntimeException{
    private  Integer status;
    private  String title;
    private  String Description;
}
