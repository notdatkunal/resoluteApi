package com.resolute.zero.controllers;

import com.resolute.zero.responses.AdminOrderRequest;
import com.resolute.zero.services.CaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class OrderController {

//        @GetMapping("/orders")
//        public List<AdminOrderResponse>
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
