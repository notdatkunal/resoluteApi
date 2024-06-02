package com.resolute.zero.helpers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseStructure<T> {
    private String message;
    private Integer status;
    private T data;
}
