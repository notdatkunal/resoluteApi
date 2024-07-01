package com.resolute.zero.controllers;

import com.resolute.zero.requests.EnquiryRequest;
import com.resolute.zero.requests.ResetPasswordRequest;
import com.resolute.zero.responses.AdminCaseResponse;
import com.resolute.zero.services.AdminService;
import com.resolute.zero.services.CaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class ApplicationController {
    @Autowired
    private CaseService caseService;
    @Autowired
    private AdminService adminService;

    @GetMapping("/list/casesByArbitratorId/{arbitratorId}")
    public List<AdminCaseResponse> adminCaseResponseList(@PathVariable Integer arbitratorId
            , @RequestParam(required = false) Integer pageNumber
            , @PageableDefault(size = 10,page=0,sort = "id") Pageable pageable){
        if (pageNumber != null) {
            pageable = PageRequest.of(pageNumber, pageable.getPageSize(), pageable.getSort());
        }

        return caseService.getCasesByArbitratorId(arbitratorId,pageable);
    }

    @GetMapping("/list/casesByBankId/{bankId}")
    public List<AdminCaseResponse> casesByBankId(@PathVariable Integer bankId
            , @RequestParam(required = false) Integer pageNumber
            , @PageableDefault(size = 10,page=0,sort = "id") Pageable pageable
    ){
        if (pageNumber != null) {
            pageable = PageRequest.of(pageNumber, pageable.getPageSize(), pageable.getSort());
        }
        return caseService.getCasesByBankId(bankId,pageable);
    }



    @PostMapping("/enquiry")
    public void enquiry(@RequestBody EnquiryRequest enquiry){


        adminService.createEntry(enquiry);
    }
    @GetMapping("/enquiries/count")
    public Long enquiryCount(){
        return adminService.enquiryCount();
    }
    @GetMapping("/list/enquiries")
    public List<EnquiryRequest> enquiryRequestList(@RequestParam(required = false) Integer pageNumber
            , @PageableDefault(size = 10,page=0,sort = "id") Pageable pageable){
        if (pageNumber != null) {
            pageable = PageRequest.of(pageNumber, pageable.getPageSize(), pageable.getSort());
        }
        return adminService.getEnquiries(pageable);
    }

    @PostMapping("/forget")
    public void forgetPassword(@RequestBody String email){
        adminService.forgetPassword(email);
    }

    @PostMapping("/reset")
    public void resetPassword(@RequestBody ResetPasswordRequest request){
            adminService.resetPassword(request);
    }


}
