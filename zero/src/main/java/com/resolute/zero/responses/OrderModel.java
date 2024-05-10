package com.resolute.zero.responses;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class OrderModel {
    Boolean interimOrder;
    Boolean awardOrder;
    Date date;
    String orderTitle;
}
