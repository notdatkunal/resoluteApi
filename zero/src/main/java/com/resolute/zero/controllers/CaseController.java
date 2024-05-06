package com.resolute.zero.controllers;

import com.resolute.zero.responses.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("/case")
public class CaseController {


    @GetMapping("/history/{caseId}")
    public CaseHistoryResponse getCaseHistory(@PathVariable Integer caseId){

        return CaseHistoryResponse.builder()
                .caseDetails(CaseDetailsResponse.builder()
                        .caseType("abc type")
                        .fillingNumber("111234/56")
                        .fillingDate(Date.from(Instant.now()))
                        .registrationDate(Date.from(Instant.now()))
                        .registrationNumber("100051/2021")
                        .registrationDate(Date.from(Instant.now()))
                        .CNRNumber("MGCC01111456732464")
                        .build())
                .caseStatus(CaseStatusResponse.builder()
                        .firstHearingDate(Date.from(Instant.now()))
                        .NextHearingDate(Date.from(Instant.now()))
                        .caseStage("final")
                        .courtAndJudgeNumber("83-court 83 ADDL sessions judge")
                        .build())
                .build();
    }
    @GetMapping("/document/{caseId}")
    public CaseDocumentsResponse getDocument(@PathVariable Integer caseId){

        return CaseDocumentsResponse.builder()
                .loanRecallNotice(LoanRecallNoticeResponse.builder()
                        .notice("LRNSN")
                        .RPAD("")
                        .build())
                .build();
    }
//    @GetMapping("/proceeding/{id}")
//    @GetMapping("/order/{id}")
//    @GetMapping("/communication/{id}")

}
