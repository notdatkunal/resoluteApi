package com.resolute.zero.controllers;


import com.resolute.zero.responses.AdminCaseResponse;
import com.resolute.zero.responses.CaseResponse;
import com.resolute.zero.services.CaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class ApplicationController {
    @Autowired
    private CaseService caseService;
    @GetMapping("/list/casesByArbitratorId/{arbitratorId}")
    public List<AdminCaseResponse> adminCaseResponseList(@PathVariable Integer arbitratorId){
        return caseService.getCasesByArbitratorId(arbitratorId);
    }

    @GetMapping("/list/casesByBankId/{bankId}")
    public List<AdminCaseResponse> casesByBankId(@PathVariable Integer bankId){
        return caseService.getCasesByBankId(bankId);
    }
}
