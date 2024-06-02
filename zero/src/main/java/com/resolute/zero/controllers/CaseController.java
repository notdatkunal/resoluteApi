package com.resolute.zero.controllers;


import com.resolute.zero.requests.HearingResponse;
import com.resolute.zero.responses.*;
import com.resolute.zero.services.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CaseController {

    @Autowired
    private CaseService caseService;

    @GetMapping("/case/history/{caseId}")
    public CaseHistoryResponse getCaseHistory(@PathVariable Integer caseId){
        return caseService.getCaseHistoryByCaseId(caseId);
    }
    @GetMapping("/case/document/{caseId}")
    public CaseDocumentsResponse getDocument(@PathVariable Integer caseId){
        return caseService.getCaseDocuments(caseId);

    }
    @GetMapping("/case/hearings/{caseId}")
    public List<HearingResponse> getProceedings(@PathVariable Integer caseId){
        return caseService.getHearingsByCaseId(caseId);
    }
    @GetMapping("/case/order/{id}")
    public String getOrder(@PathVariable Integer id){
        return "null"+id;
//                OrderResponse.builder()
//                .awardOrder(OrderModel.builder().orderTitle("randomTitle").date(Date.from(Instant.now())).awardOrder(true).build())
//                .interimOrder(OrderModel.builder().orderTitle("randomTitle").date(Date.from(Instant.now())).interimOrder(true).build())
//                .orders(List.of(OrderModel.builder().orderTitle("randomTitle").date(Date.from(Instant.now())).build(),OrderModel.builder().orderTitle("randomTitle").date(Date.from(Instant.now())).build(),OrderModel.builder().orderTitle("randomTitle").date(Date.from(Instant.now())).build()))
//                .build();
    }

    @GetMapping("/case/communication/{id}")
    public CommunicationResponse getComm(@PathVariable Integer id){
        return  caseService.getCommunicationByCaseId(id);
    }
}
