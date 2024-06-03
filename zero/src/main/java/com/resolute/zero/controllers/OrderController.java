package com.resolute.zero.controllers;

import com.resolute.zero.responses.AdminOrderRequest;
import com.resolute.zero.responses.AdminOrderResponse;
import com.resolute.zero.services.CaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class OrderController {

        @GetMapping("/admin/orders/{caseId}")
        public List<AdminOrderResponse> getOrdersByCaseId(@PathVariable Integer caseId){
            return caseService.getCaseOrdersByCaseId(caseId);
        }
    @GetMapping("/admin/order/{orderId}")
    public AdminOrderResponse getOrderById(@PathVariable Integer orderId){
           return caseService.getOrderById(orderId);
    }
    @PutMapping("/admin/orderDate/{orderId}")
    public void updateOrderDateById(@PathVariable Integer orderId,@ModelAttribute Date date){
            caseService.updateOrderDateById(orderId,date);
    }
    @PutMapping("/admin/orderType/{orderId}")
    public void updateOrderTypeById(@PathVariable Integer orderId,@ModelAttribute String type){
                caseService.updateOrderTypeById(orderId,type);
    }




    @DeleteMapping("/admin/order/{orderId}")
    public void deleteOrderById(@PathVariable Integer orderId){

            caseService.deleteOrderById(orderId);
    }
    @Autowired
    private CaseService caseService;
    @PostMapping("/admin/order/{caseId}")
    public ResponseEntity<?> createOrder(
            @PathVariable Integer caseId
            ,@ModelAttribute AdminOrderRequest adminOrder
    ) throws IOException {
        return caseService.createOrderByCaseId(adminOrder);
    }
}
