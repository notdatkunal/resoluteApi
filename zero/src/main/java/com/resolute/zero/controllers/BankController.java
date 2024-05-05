package com.resolute.zero.controllers;

import com.resolute.zero.requests.CaseRequest;
import com.resolute.zero.requests.SearchRequest;
import com.resolute.zero.responses.CaseResponse;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankController {
    @GetMapping("/cases/{bankId}")
    public List<CaseResponse> getCases(@PathVariable Integer bankId){
        return List.of(
                CaseResponse.builder().caseNo(1).serialNo(bankId).registrationDate(Date.from(Instant.now())).fillingDate(Date.from(Instant.now())).registrationNumber(5431532).caseType("xyz").registrationDate(Date.from(Instant.now())).build(),
                CaseResponse.builder().caseNo(1).serialNo(bankId).registrationDate(Date.from(Instant.now())).fillingDate(Date.from(Instant.now())).registrationNumber(5431532).caseType("xyz").registrationDate(Date.from(Instant.now())).build(),
                CaseResponse.builder().caseNo(1).serialNo(bankId).registrationDate(Date.from(Instant.now())).fillingDate(Date.from(Instant.now())).registrationNumber(5431532).caseType("xyz").registrationDate(Date.from(Instant.now())).build()
        );
    }
    @GetMapping("/search/{bankId}")
    public List<CaseResponse> searchCase(@PathVariable Integer bankId,@RequestBody(required = false) SearchRequest searchRequest){

        if(searchRequest==null){
            return null;
        }

        var list =  List.of(CaseResponse.builder()
                        .caseNo(1)
                        .serialNo(1)
                        .registrationDate(Date.from(Instant.now()))
                        .fillingDate(Date.from(Instant.now()))
                        .registrationNumber(bankId)
                        .caseType(searchRequest.getSearchParameter())
                        .registrationDate(Date.from(Instant.now()))
                        .build(),
                CaseResponse.builder()
                        .caseNo(2)
                        .serialNo(2)
                        .caseType("xyz"+searchRequest.getSearchParameter())
                        .registrationDate(Date.from(Instant.now()))
                        .fillingDate(Date.from(Instant.now()))
                        .registrationNumber(bankId)
                        .build(),
                CaseResponse.builder()
                        .caseNo(3)
                        .serialNo(3)
                        .registrationDate(Date.from(Instant.now()))
                        .fillingDate(Date.from(Instant.now()))
                        .registrationNumber(bankId)
                        .caseType("mno"+searchRequest.getSearchParameter())
                        .registrationDate(Date.from(Instant.now()))
                        .build()
        );

        if(searchRequest.getDate()!=null)
        {
            list.forEach(item->{
                item.setRegistrationDate(searchRequest.getDate());
            });
        }

        return list;
    }
}
