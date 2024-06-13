package com.resolute.zero.controllers;

import com.resolute.zero.requests.AdminCaseRequest;
import com.resolute.zero.requests.ArbitratorRequest;
import com.resolute.zero.requests.BankRequest;
import com.resolute.zero.responses.*;
import com.resolute.zero.services.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
//search case by bank id
public class AdminCaseController {
    @Autowired
    private final AdminService adminService;

    @GetMapping("/admin/bank/{bankId}")
    public BankResponse getBankById(@PathVariable Integer bankId){
        return adminService.getBankById(bankId);
    }
    @GetMapping("/admin/caseByBankIdAndType/{bankId}")
    public List<AdminCaseResponse> getCasesByBankIdAndType(@PathVariable Integer bankId,@RequestParam String type){
        return adminService.getCasesByBankIdAndType(bankId,type);
    }

    @GetMapping("/admin/caseTypeCount/{bankId}")
    public Map<String,Long> getTypeCountsByBankId(@PathVariable Integer bankId){
        return adminService.getTypeCountsByBankId(bankId);
    }
    @PostMapping("/admin/caseType/{type}")
    public void createType(@PathVariable String type){
        adminService.createType(type);
    }

}
