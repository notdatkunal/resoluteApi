package com.resolute.zero.controllers;

import com.resolute.zero.requests.CaseHearingRequest;
import com.resolute.zero.requests.CaseProceedingsResponse;
import com.resolute.zero.requests.HearingResponse;
import com.resolute.zero.responses.*;
import com.resolute.zero.services.CaseService;
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


    @PostMapping("/hearing/{caseId}")
    public void  createHearingByCaseId(@PathVariable Integer caseId, @RequestBody CaseHearingRequest caseHearingRequest){
        System.out.println(caseHearingRequest.getHearingDate());
        caseService.createHearingByCaseId(caseId,caseHearingRequest);
    }


    @GetMapping("/history/{caseId}")
    public CaseHistoryResponse getCaseHistory(@PathVariable Integer caseId){
        return caseService.getCaseHistoryByCaseId(caseId);
    }
    @GetMapping("/document/{caseId}")
    public CaseDocumentsResponse getDocument(@PathVariable Integer caseId){
        return caseService.getCaseDocuments(caseId);

    }
    @GetMapping("/proceeding/{id}")
    public CaseProceedingsResponse getProceedings(@PathVariable Integer id){

        return CaseProceedingsResponse.builder()
                .hearings(List.of(HearingResponse.builder().hearingDate(Date.from(Instant.now())).minutesOfMeetings("g-C3N4").build(),HearingResponse.builder().hearingDate(Date.from(Instant.now())).minutesOfMeetings("g-C3N4").build(),HearingResponse.builder().current(true).hearingDate(Date.from(Instant.now())).minutesOfMeetings("g-C3N4").build()))
                .build();
    }
    @GetMapping("/order/{id}")
    public OrderResponse getOrder(@PathVariable Integer id){



        return OrderResponse.builder()
                .awardOrder(OrderModel.builder().orderTitle("randomTitle").date(Date.from(Instant.now())).awardOrder(true).build())
                .interimOrder(OrderModel.builder().orderTitle("randomTitle").date(Date.from(Instant.now())).interimOrder(true).build())
                .orders(List.of(OrderModel.builder().orderTitle("randomTitle").date(Date.from(Instant.now())).build(),OrderModel.builder().orderTitle("randomTitle").date(Date.from(Instant.now())).build(),OrderModel.builder().orderTitle("randomTitle").date(Date.from(Instant.now())).build()))
                .build();
    }

    @GetMapping("/communication/{id}")
    public CommunicationResponse getComm(@PathVariable Integer id){
        return  caseService.getCommunicationByCaseId(id);
    }
}
