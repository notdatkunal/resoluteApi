package com.resolute.zero.controllers;

import com.resolute.zero.responses.CaseDetailsResponse;
import com.resolute.zero.responses.CaseHistoryResponse;
import com.resolute.zero.responses.CaseStatusResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/case")
public class CaseController {


    @GetMapping("/history/{id}")
    public CaseHistoryResponse getCaseHistory(@PathVariable Integer id){

        return CaseHistoryResponse.builder()
                .caseDetails(CaseDetailsResponse.builder()
                        .caseType("abc type")
                        .fillingNumber("111234/56")

                        .build())
                .caseStatus(CaseStatusResponse.builder().build())
                .build();
    }
//    @GetMapping("/document/{id}")
//    @GetMapping("/proceeding/{id}")
//    @GetMapping("/order/{id}")
//    @GetMapping("/communication/{id}")

}
