package com.resolute.zero.controllers;


import com.resolute.zero.requests.SearchRequest;
import com.resolute.zero.responses.CaseResponse;

import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
public class BankController {
    @GetMapping("/bank/cases/{bankId}")
    public List<CaseResponse> getCases(@PathVariable Integer bankId){

        return List.of(
                CaseResponse.builder().registrationDate(Date.from(Instant.now())).fillingDate(Date.from(Instant.now())).caseType("xyz").registrationDate(Date.from(Instant.now())).build(),
                CaseResponse.builder().registrationDate(Date.from(Instant.now())).fillingDate(Date.from(Instant.now())).caseType("xyz").registrationDate(Date.from(Instant.now())).build(),
                CaseResponse.builder().registrationDate(Date.from(Instant.now())).fillingDate(Date.from(Instant.now())).caseType("xyz").registrationDate(Date.from(Instant.now())).build()
        );
    }
    @GetMapping("/bank/search/{bankId}")
    public List<CaseResponse> searchCase(@PathVariable Integer bankId,@RequestBody(required = false) SearchRequest searchRequest){

        if(searchRequest==null){
            return null;
        }
        return List.of(CaseResponse.builder()
                        .registrationDate(Date.from(Instant.now()))
                        .fillingDate(Date.from(Instant.now()))
                        .registrationDate(Date.from(Instant.now()))
                        .build(),
                CaseResponse.builder()
                        .registrationDate(Date.from(Instant.now()))
                        .fillingDate(Date.from(Instant.now()))
                        .build(),
                CaseResponse.builder()
                        .registrationDate(Date.from(Instant.now()))
                        .fillingDate(Date.from(Instant.now()))
                        .registrationDate(Date.from(Instant.now()))
                        .build()
        );
    }
}
