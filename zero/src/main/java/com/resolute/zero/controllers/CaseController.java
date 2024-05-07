package com.resolute.zero.controllers;

import com.resolute.zero.models.CaseProceeding;
import com.resolute.zero.requests.CaseProceedingsResponse;
import com.resolute.zero.responses.*;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("/case")
@CrossOrigin("*")
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
                        .notice("LRNNOT000044")
                        .RPAD("LRNNOT000044")
                        .tracking("LRNNOT000044")
                        .build())
                .intentLetter(IntentLetterResponse.builder()
                        .notice("LRNNOT000044")
                        .RPAD("LRNNOT000044")
                        .tracking("LRNNOT000044")
                        .build())
                .referenceLetter(ReferenceLetterResponse.builder()
                        .notice("LRNNOT000044")
                        .RPAD("LRNNOT000044")
                        .tracking("LRNNOT000044")
                        .build())
                .concentLetter(ConcentLetterResponse.builder()
                        .notice("LRNNOT000044")
                        .RPAD("LRNNOT000044")
                        .tracking("LRNNOT000044")
                        .statementOfClaim("LRNNOT000044")
                        .build())
                .intimationLetter(IntimationLetterResponse.builder()
                        .notice("LRNNOT000044")
                        .RPAD("LRNNOT000044")
                        .tracking("LRNNOT000044")
                        .affidavit("LRNNOT000044")
                        .build())
                .awardLetter(AwardLetterResponse.builder()
                        .notice("LRNNOT000044")
                        .RPAD("LRNNOT000044")
                        .tracking("LRNNOT000044")
                        .roznama("LRNNOT000044")
                        .bankDocument("LRNNOT000044")
                        .build())
                .build();

    }
//    @GetMapping("/proceeding/{id}")
//    public CaseProceedingsResponse getProceedings(@PathVariable Integer id){
//        return
//    }
//    @GetMapping("/order/{id}")
//    @GetMapping("/communication/{id}")

}
