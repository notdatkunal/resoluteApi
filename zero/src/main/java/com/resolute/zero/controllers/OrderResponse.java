package com.resolute.zero.controllers;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrderResponse {

    OrderModel interimOrder;
    OrderModel awardOrder;
    List<OrderModel> orders;
}
@Data
@Builder
class OrderModel{
    Boolean interimOrder;
    Boolean awardOrder;
    Date date;
    String orderTitle;
}
