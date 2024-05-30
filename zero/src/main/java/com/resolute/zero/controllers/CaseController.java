package com.resolute.zero.controllers;


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

    @GetMapping("/history/{caseId}")
    public CaseHistoryResponse getCaseHistory(@PathVariable Integer caseId){
        return caseService.getCaseHistoryByCaseId(caseId);
    }
    @GetMapping("/document/{caseId}")
    public CaseDocumentsResponse getDocument(@PathVariable Integer caseId){
        return caseService.getCaseDocuments(caseId);

    }
    @GetMapping("/hearings/{caseId}")
    public List<HearingResponse> getProceedings(@PathVariable Integer caseId){
        return caseService.getHearingsByCaseId(caseId);
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
