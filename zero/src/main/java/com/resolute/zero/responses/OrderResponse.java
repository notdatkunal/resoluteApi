package com.resolute.zero.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderResponse {

    OrderModel interimOrder;
    OrderModel awardOrder;
    List<OrderModel> orders;
}
