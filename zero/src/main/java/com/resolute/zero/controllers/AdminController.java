package com.resolute.zero.controllers;

import com.resolute.zero.configurations.SampleDocuments;
import com.resolute.zero.requests.*;
import com.resolute.zero.responses.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
@Slf4j
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



    @GetMapping("/documents")
    public List<DocumentResponse> listDocuments(@RequestParam(required = false) String documentType){
        log.info(documentType);
       var list =  List.of(
                DocumentResponse.builder().documentByteString(SampleDocuments.SAMPLE_DOC).documentTitle("abc").type("arbitrator").uploadDate(Date.from(Instant.now())).build(),
                DocumentResponse.builder().documentByteString(SampleDocuments.SAMPLE_DOC).documentTitle("abc").type("arbitrator").uploadDate(Date.from(Instant.now())).build(),
                DocumentResponse.builder().documentByteString(SampleDocuments.SAMPLE_DOC).documentTitle("abc").type("arbitrator").uploadDate(Date.from(Instant.now())).build()

        );

       if(documentType!=null)
           list.forEach(item->item.setType(documentType));

       return list;

    }
    @PostMapping(value = "/document")
    public void createDocument(@RequestParam String documentTitle,@RequestParam String type,@RequestParam String caseId,@RequestParam("document") MultipartFile file) throws IOException {

        log.info(Arrays.toString(file.getBytes()));
        log.info(documentTitle);
        log.info(caseId);
        log.info(type);


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

    @GetMapping("/search/case")
    public List<CaseResponse> searchCase(@RequestBody SearchRequest searchRequest) throws ParseException {



       var list =    List.of(CaseResponse.builder()
                        .caseNo(1)
                        .serialNo(1)
                       .registrationDate(Date.from(Instant.now()))
                       .fillingDate(Date.from(Instant.now()))
                       .registrationNumber(5431532)
                        .caseType(searchRequest.getSearchParameter())
                        .registrationDate(Date.from(Instant.now()))
                        .build(),
                CaseResponse.builder()
                        .caseNo(2)
                        .serialNo(2)
                        .caseType("xyz"+searchRequest.getSearchParameter())
                        .registrationDate(Date.from(Instant.now()))
                        .fillingDate(Date.from(Instant.now()))
                        .registrationNumber(5431532)
                        .build(),
                CaseResponse.builder()
                        .caseNo(3)
                        .serialNo(3)
                        .registrationDate(Date.from(Instant.now()))
                        .fillingDate(Date.from(Instant.now()))
                        .registrationNumber(5431532)
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
