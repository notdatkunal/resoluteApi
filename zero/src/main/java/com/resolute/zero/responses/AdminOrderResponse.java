package com.resolute.zero.responses;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class AdminOrderResponse {
    String type;
    String filName;
    Date date;
}
