package com.resolute.zero.controllers;

import com.resolute.zero.requests.*;
import com.resolute.zero.responses.*;
import com.resolute.zero.services.AdminService;
import com.resolute.zero.services.CaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private CaseService caseService;

    @PostMapping("/admin/hearing/{caseId}")
    public void  createHearingByCaseId(@PathVariable Integer caseId, @RequestBody CaseHearingRequest caseHearingRequest){
        caseService.createHearingByCaseId(caseId,CaseHearingRequest.builder().hearingDate(caseHearingRequest.getHearingDate()).build());
    }
    @PutMapping("/admin/hearing/{hearingId}")
    public void  createHearingByCaseId(@RequestHeader CaseHearingRequest date,@PathVariable Integer hearingId){
        caseService.updateHearingByHearingId(hearingId,date.getHearingDate());
    }

    @Autowired
    private final AdminService adminService;


    @PutMapping("/admin/case/{caseId}")
    public void addCase(@RequestBody AdminCaseRequest request,@PathVariable Integer caseId){
        adminService.updateCase(request,caseId);
    }

    @PostMapping("/admin/case")
    public void addCase(@RequestBody AdminCaseRequest request  ){

        adminService.saveCase(request);
    }

    @PostMapping("/admin/borrower")

    public void addBorrower(@RequestBody BorrowerRequest request){


        adminService.addBorrower(request);
    }

    @PutMapping("/admin/arbitrator/{arbitratorId}")
    public void updateArbitrator(@RequestBody ArbitratorRequest request,@PathVariable Integer arbitratorId){

        adminService.updateArbitrator(request,arbitratorId);
    }

    @PostMapping("/admin/arbitrator")

    public void addArbitrator(@RequestBody ArbitratorRequest request){


        adminService.addArbitrator(request);
    }



    @GetMapping("/admin/hearings/{caseId}")
    public List<HearingResponse> getProceedings(@PathVariable Integer caseId){
        return caseService.getHearingsByCaseId(caseId);
    }


    @DeleteMapping("/admin/hearing/{hearingId}")
    public void  deleteHearingByCaseId(@PathVariable Integer hearingId){
        caseService.deleteHearingById(hearingId);
    }

    @PostMapping("/admin/bank")
    public void addBank(@RequestBody BankRequest request){
        adminService.addBank(request);
    }
    @PutMapping("/admin/bank/{bankId}")
    public void updateBank(@RequestBody BankRequest request,@PathVariable Integer bankId) {
    	adminService.updateBank(request,bankId);
    }

    /**
     * please read the service documentation before using this
     * */
    @GetMapping("/admin/documents")
    public List<DocumentResponse> listDocuments(){
        return adminService.getDocumentList();

    }

    @GetMapping("/admin/arbitrator/{arbitratorId}")
    public ArbitratorResponse getArbitratorById(@PathVariable Integer arbitratorId){
        return adminService.getArbitratorById(arbitratorId);
    }
    @GetMapping("/admin/arbitrator")
    public List<ArbitratorResponse> getAllArbitrators(){
        return adminService.getArbitratorList();

    }
    @GetMapping("/admin/case/{caseId}")
    public AdminCaseResponse getCaseById(@PathVariable Integer caseId){
        return adminService.getCaseById(caseId);
    }

    @GetMapping("/admin/search/case")
    public List<CaseResponse> searchCase(@RequestParam(value = "parameter",required = false) String searchParameter,@RequestParam(value = "date",required = false) Date date) {
        System.out.println("the date is :"+date);
            return adminService.getSearchResponse(searchParameter,date);
    }
    @GetMapping("/admin/case")
    public List<CaseResponse> getAllCases(){
        return   adminService.getCaseList();
    }


    @GetMapping("/admin/borrower/{borrowerId}")
    public BorrowerResponse getBorrowerById(@PathVariable Integer borrowerId ){
    return adminService.getBorrowerById(borrowerId);
    }
    @GetMapping("/admin/borrower")
    public List<BorrowerResponse> getAllBorrowers(){
        return adminService.getBorrwerList();

    }









    
    @GetMapping("/admin/bank/{bankId}")
    public BankResponse getBankById(@PathVariable Integer bankId){
        return adminService.getBankById(bankId);
    }
    @GetMapping("/admin/bank")
    public List<BankResponse> getAllBanks(){
        return adminService.getBanksList();

    }

}
