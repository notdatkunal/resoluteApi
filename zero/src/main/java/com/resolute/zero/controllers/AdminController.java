package com.resolute.zero.controllers;


import com.resolute.zero.requests.*;
import com.resolute.zero.responses.*;
import com.resolute.zero.services.AdminService;
import com.resolute.zero.utilities.Constants;
import com.resolute.zero.utilities.ApplicationUtility;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private final AdminService adminService;

    @PostMapping("/case")
    public void addCase(@RequestBody AdminCaseRequest request, HttpSession session){
        ApplicationUtility.authenticate(session,"admin");
        adminService.saveCase(request);

    }

    @PostMapping("/borrower")
    public void addBorrower(@RequestBody BorrowerRequest request, HttpSession session){
        ApplicationUtility.authenticate(session,"admin");
        adminService.addBorrower(request);

    }

    @PostMapping("/arbitrator")
    public void addArbitrator(@RequestBody ArbitratorRequest request, HttpSession session){
        ApplicationUtility.authenticate(session,"admin");
        adminService.addArbitrator(request);
    }

    @PostMapping("/bank")
    public void addBank(@RequestBody BankRequest request, HttpSession session){
        ApplicationUtility.authenticate(session,"admin");
        adminService.addBank(request);
    }


    /**
     * please read the service documentation before using this
     * */
    @GetMapping("/documents")
    public List<DocumentResponse> listDocuments(
            HttpSession session){
        ApplicationUtility.authenticate(session,"admin");
        return adminService.getDocumentList();

    }

    @GetMapping("/arbitrator/{arbitratorId}")
    public ArbitratorResponse getArbitratorById(@PathVariable Integer arbitratorId, HttpSession session){
        ApplicationUtility.authenticate(session,"admin");
        return adminService.getArbitratorById(arbitratorId);
    }
    @GetMapping("/arbitrator")
    public List<ArbitratorResponse> getAllArbitrators(HttpSession session){
        ApplicationUtility.authenticate(session,"admin");
        return adminService.getArbitratorList();

    }




    @GetMapping("/case/{caseId}")
    public CaseResponse getCaseById(@PathVariable Integer caseId, HttpSession session){
        ApplicationUtility.authenticate(session,"admin");
        return adminService.getCaseById(caseId);
    }

    @GetMapping("/search/case")
    public List<CaseResponse> searchCase(@RequestParam(value = "parameter",required = false) String searchParameter,@RequestParam(value = "date",required = false) Date date, HttpSession session) {
    ApplicationUtility.authenticate(session,"admin");
    return adminService.getSearchResponse(searchParameter,date);
    }
    @GetMapping("/case")
    public List<CaseResponse> getAllCases(HttpSession session){
    ApplicationUtility.authenticate(session,"admin");
        return   List.of(CaseResponse.builder()
                        .caseNo("1")
                        .caseType("abc")
                        .registrationDate(Date.from(Instant.now()))
                        .build(),
                CaseResponse.builder()
                        .caseNo("2")
                        .caseType("xyz")
                        .registrationDate(Date.from(Instant.now()))
                        .build(),
                CaseResponse.builder()
                        .caseNo("3")
                        .caseType("mno")
                        .registrationDate(Date.from(Instant.now()))
                        .build()
        );

    }











    @GetMapping("/borrower/{borrowerId}")
    public BorrowerResponse getBorrowerById(@PathVariable Integer borrowerId, HttpSession session){
ApplicationUtility.authenticate(session,"admin");
        return BorrowerResponse.builder()
                .borrowerId(borrowerId)
                .serialNo(borrowerId)
                .borrowerName("abc")
                .registrationDate(Date.from(Instant.now()))
                .build();
    }
    @GetMapping("/borrower")
    public List<BorrowerResponse> getAllBorrowers( HttpSession session){
ApplicationUtility.authenticate(session,"admin");

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
















    @GetMapping("/bank/{bankId}")
    public BankResponse getBankById(@PathVariable Integer bankId, HttpSession session){


    ApplicationUtility.authenticate(session,"admin");
        return BankResponse.builder()
                .bankId(bankId)
                .serialNo(bankId)
                .bankName("abc")
                .registrationDate(Date.from(Instant.now()))
                .build();
    }
    @GetMapping("/bank")
    public List<BankResponse> getAllBanks( HttpSession session){
ApplicationUtility.authenticate(session,"admin");

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
