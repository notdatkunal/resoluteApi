package com.resolute.zero.controllers;


import com.resolute.zero.responses.*;
import com.resolute.zero.services.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public List<AdminCaseResponse> getCasesByBankIdAndType(@PathVariable Integer bankId
            , @RequestParam(required = false) Integer pageNumber
            , @PageableDefault(size = 10,page=0,sort = "id") Pageable pageable
            ,@RequestParam String type,@RequestParam(required = false) String status){
        if (pageNumber != null) {
            pageable = PageRequest.of(pageNumber, pageable.getPageSize(), pageable.getSort());
        }
        System.out.println("this is status"+status);
        if(status!=null)
            return adminService.getCasesByBankIdAndTypeAndStatus(bankId,type,status,pageable);
        return adminService.getCasesByBankIdAndType(bankId,type,pageable);
    }
    @GetMapping("/admin/caseCountByTypeAndStatus/{bankId}")
    public Long getCountOfCaseByBankIdAndTypeAndStatus(
            @PathVariable Integer bankId,@RequestParam String type,@RequestParam String status){

        return adminService.getCountOfCaseByBankIdAndTypeAndStatus(bankId,type,status);
    }
    @GetMapping("/admin/caseCount/{bankId}")
    public Long getCaseCountByBankId(@PathVariable Integer bankId){
        return adminService.getCaseCountByBankId(bankId);
    }
    @GetMapping("/admin/caseCount")
    public Long getTotalCaseCount(){
        return adminService.getTotalCaseCount();
    }
    @GetMapping("/admin/arbitratorCount")
    public Long getArbitratorCount(){
        return adminService.getArbitratorCount();
    }
    @GetMapping("/admin/bankCount")
    public Long getBankCount(){return adminService.getBankCount();}
    @GetMapping("/admin/caseTypeCount/{bankId}")
    public Map<String,Long> getTypeCountsByBankId(@PathVariable Integer bankId){
        return adminService.getTypeCountsByBankId(bankId);
    }
    @PostMapping("/admin/caseType/{type}")
    public void createType(@PathVariable String type){
        adminService.createType(type);
    }

}
