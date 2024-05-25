package com.resolute.zero.controllers;

import com.resolute.zero.requests.CaseProceedingsResponse;
import com.resolute.zero.requests.HearingResponse;
import com.resolute.zero.responses.*;
import com.resolute.zero.services.CaseService;
import com.resolute.zero.utilities.ApplicationUtility;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/case")
@CrossOrigin("*")
public class CaseController {

    @Autowired
    private CaseService caseService;

    @GetMapping("/history/{caseId}")
    public CaseHistoryResponse getCaseHistory(@PathVariable Integer caseId, HttpSession session){

        return caseService.getCaseHistoryByCaseId(caseId);
    }
    @GetMapping("/document/{caseId}")
    public CaseDocumentsResponse getDocument(@PathVariable Integer caseId,HttpSession session){

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
    @GetMapping("/proceeding/{id}")
    public CaseProceedingsResponse getProceedings(@PathVariable Integer id,HttpSession session){

        return CaseProceedingsResponse.builder()
                .hearings(List.of(HearingResponse.builder().hearingDate(Date.from(Instant.now())).minutesOfMeetings("g-C3N4").build(),HearingResponse.builder().hearingDate(Date.from(Instant.now())).minutesOfMeetings("g-C3N4").build(),HearingResponse.builder().current(true).hearingDate(Date.from(Instant.now())).minutesOfMeetings("g-C3N4").build()))
                .build();
    }
    @GetMapping("/order/{id}")
    public OrderResponse getOrder(@PathVariable Integer id,HttpSession session){


        OrderModel.builder().build() ;
        return OrderResponse.builder()
                .awardOrder(OrderModel.builder().orderTitle("randomTitle").date(Date.from(Instant.now())).awardOrder(true).build())
                .interimOrder(OrderModel.builder().orderTitle("randomTitle").date(Date.from(Instant.now())).interimOrder(true).build())
                .orders(List.of(OrderModel.builder().orderTitle("randomTitle").date(Date.from(Instant.now())).build(),OrderModel.builder().orderTitle("randomTitle").date(Date.from(Instant.now())).build(),OrderModel.builder().orderTitle("randomTitle").date(Date.from(Instant.now())).build()))
                .build();
    }

    @GetMapping("/communication/{id}")
    public CommunicationResponse getComm(@PathVariable Integer id,HttpSession session){


        return CommunicationResponse.builder()
                .dates(List.of(CommDateResponse.builder().date(Date.from(Instant.now())).emailComm("emailDoc").emailCommTitle("emailTitleLink").whatsAppComm("whatsappComm").whatsAppCommTitle("whatsappCommTitleLink").textComm("textComm").textCommTitle("textCommTitleLink").build(),CommDateResponse.builder().date(Date.from(Instant.now())).emailComm("emailDoc").emailCommTitle("emailTitleLink").whatsAppComm("whatsappComm").whatsAppCommTitle("whatsappCommTitleLink").textComm("textComm").textCommTitle("textCommTitleLink").build(),CommDateResponse.builder().date(Date.from(Instant.now())).emailComm("emailDoc").emailCommTitle("emailTitleLink").whatsAppComm("whatsappComm").whatsAppCommTitle("whatsappCommTitleLink").textComm("textComm").textCommTitle("textCommTitleLink").build()))
                .build();
    }
}
