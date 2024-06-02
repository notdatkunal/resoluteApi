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
//    @GetMapping("/order/{orderId}")
//    @PutMapping("/order/{orderId}")
//    @DeleteMapping("/order/{orderId}")
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
