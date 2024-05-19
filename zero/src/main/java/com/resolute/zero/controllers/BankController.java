package com.resolute.zero.controllers;


import com.resolute.zero.requests.SearchRequest;
import com.resolute.zero.responses.CaseResponse;
import com.resolute.zero.utilities.ApplicationUtility;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bank")
@CrossOrigin("*")
public class BankController {
    @GetMapping("/cases/{bankId}")
    public List<CaseResponse> getCases(@PathVariable Integer bankId, HttpSession session){
        ApplicationUtility.authenticate(session,"bank");
        return List.of(
                CaseResponse.builder().registrationDate(Date.from(Instant.now())).fillingDate(Date.from(Instant.now())).caseType("xyz").registrationDate(Date.from(Instant.now())).build(),
                CaseResponse.builder().registrationDate(Date.from(Instant.now())).fillingDate(Date.from(Instant.now())).caseType("xyz").registrationDate(Date.from(Instant.now())).build(),
                CaseResponse.builder().registrationDate(Date.from(Instant.now())).fillingDate(Date.from(Instant.now())).caseType("xyz").registrationDate(Date.from(Instant.now())).build()
        );
    }
    @GetMapping("/search/{bankId}")
    public List<CaseResponse> searchCase(HttpSession session,@PathVariable Integer bankId,@RequestBody(required = false) SearchRequest searchRequest){
        ApplicationUtility.authenticate(session,"bank");
        if(searchRequest==null){
            return null;
        }
        var list =  List.of(CaseResponse.builder()
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
        return list;
    }
}
