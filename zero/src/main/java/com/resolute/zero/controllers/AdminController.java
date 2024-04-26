package com.resolute.zero.controllers;

import com.resolute.zero.requests.ArbitratorRequest;
import com.resolute.zero.requests.BankRequest;
import com.resolute.zero.requests.BorrowerRequest;
import com.resolute.zero.requests.CaseRequest;
import com.resolute.zero.responses.ArbitratorResponse;
import com.resolute.zero.responses.BankResponse;
import com.resolute.zero.responses.BorrowerResponse;
import com.resolute.zero.responses.CaseResponse;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @PostMapping("/case")
    public void addCase(@RequestBody CaseRequest request){
        System.out.println(request);
    }

    @PostMapping("/borrower")
    public void addBorrower(@RequestBody BorrowerRequest request){
        System.out.println(request);
    }

    @PostMapping("/arbitrator")
    public void addArbitrator(@RequestBody ArbitratorRequest request){
        System.out.println(request);
    }

    @PostMapping("/bank")
    public void addBank(@RequestBody BankRequest request){
        System.out.println(request);
    }


    @GetMapping("arbitrator/{arbitratorId}")
    public ArbitratorResponse getArbitratorById(@PathVariable Integer arbitratorId){

        return ArbitratorResponse.builder()
                .arbitratorId(arbitratorId)
                .serialNo(arbitratorId)
                .arbitratorName("abc")
                .registrationDate(Date.from(Instant.now()))
                .build();
    }
    @GetMapping("/arbitrator")
    public List<ArbitratorResponse> getAllArbitrators(){


        return   List.of(ArbitratorResponse.builder()
                        .arbitratorId(1)
                        .serialNo(1)
                        .arbitratorName("abc")
                        .registrationDate(Date.from(Instant.now()))
                .build(),
                ArbitratorResponse.builder()
                        .arbitratorId(2)
                        .serialNo(2)
                        .arbitratorName("xyz")
                        .registrationDate(Date.from(Instant.now()))
                        .build(),
                ArbitratorResponse.builder()
                        .arbitratorId(3)
                        .serialNo(3)
                        .arbitratorName("mno")
                        .registrationDate(Date.from(Instant.now()))
                        .build()
                );

    }











    @GetMapping("case/{caseId}")
    public CaseResponse getCaseById(@PathVariable Integer caseId){

        return CaseResponse.builder()
                .caseNo(caseId)
                .serialNo(caseId)
                .caseType("abc")
                .registrationDate(Date.from(Instant.now()))
                .build();
    }
    @GetMapping("/case")
    public List<CaseResponse> getAllCases(){


        return   List.of(CaseResponse.builder()
                        .caseNo(1)
                        .serialNo(1)
                        .caseType("abc")
                        .registrationDate(Date.from(Instant.now()))
                        .build(),
                CaseResponse.builder()
                        .caseNo(2)
                        .serialNo(2)
                        .caseType("xyz")
                        .registrationDate(Date.from(Instant.now()))
                        .build(),
                CaseResponse.builder()
                        .caseNo(3)
                        .serialNo(3)
                        .caseType("mno")
                        .registrationDate(Date.from(Instant.now()))
                        .build()
        );

    }











    @GetMapping("borrower/{borrowerId}")
    public BorrowerResponse getBorrowerById(@PathVariable Integer borrowerId){

        return BorrowerResponse.builder()
                .borrowerId(borrowerId)
                .serialNo(borrowerId)
                .borrowerName("abc")
                .registrationDate(Date.from(Instant.now()))
                .build();
    }
    @GetMapping("/borrower")
    public List<BorrowerResponse> getAllBorrowers(){


        return   List.of(BorrowerResponse.builder()
                        .borrowerId(1)
                        .serialNo(1)
                        .borrowerName("abc")
                        .registrationDate(Date.from(Instant.now()))
                        .build(),
                BorrowerResponse.builder()
                        .borrowerId(2)
                        .serialNo(2)
                        .borrowerName("xyz")
                        .registrationDate(Date.from(Instant.now()))
                        .build(),
                BorrowerResponse.builder()
                        .borrowerId(3)
                        .serialNo(3)
                        .borrowerName("mno")
                        .registrationDate(Date.from(Instant.now()))
                        .build()
        );

    }
















    @GetMapping("bank/{bankId}")
    public BankResponse getBankById(@PathVariable Integer bankId){

        return BankResponse.builder()
                .bankId(bankId)
                .serialNo(bankId)
                .bankName("abc")
                .registrationDate(Date.from(Instant.now()))
                .build();
    }
    @GetMapping("/bank")
    public List<BankResponse> getAllBanks(){


        return   List.of(BankResponse.builder()
                        .bankId(1)
                        .serialNo(1)
                        .bankName("abc")
                        .registrationDate(Date.from(Instant.now()))
                        .build(),
                BankResponse.builder()
                        .bankId(2)
                        .serialNo(2)
                        .bankName("xyz")
                        .registrationDate(Date.from(Instant.now()))
                        .build(),
                BankResponse.builder()
                        .bankId(3)
                        .serialNo(3)
                        .bankName("mno")
                        .registrationDate(Date.from(Instant.now()))
                        .build()
        );

    }

}
