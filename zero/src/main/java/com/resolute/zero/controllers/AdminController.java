package com.resolute.zero.controllers;

import com.resolute.zero.requests.*;
import com.resolute.zero.responses.*;
import com.resolute.zero.services.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class AdminController {
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
//    @PostMapping("/admin/borrower")
//    public void addBorrower(@RequestBody BorrowerRequest request){
//        adminService.addBorrower(request);
//    }
    @PutMapping("/admin/arbitrator/{arbitratorId}")
    public void updateArbitrator(@RequestBody ArbitratorRequest request,@PathVariable Integer arbitratorId){
        adminService.updateArbitrator(request,arbitratorId);
    }
    @PostMapping("/admin/arbitrator")
    public void addArbitrator(@RequestBody ArbitratorRequest request){
        adminService.addArbitrator(request);
    }
    @PostMapping("/admin/bank")
    public void addBank(@RequestBody BankRequest request){
        adminService.addBank(request);
    }
    @PutMapping("/admin/bank/{bankId}")
    public void updateBank(@RequestBody BankRequest request,@PathVariable Integer bankId) {
    	adminService.updateBank(request,bankId);
    }

    @GetMapping("/admin/arbitrator/{arbitratorId}")
    public ArbitratorResponse getArbitratorById(@PathVariable Integer arbitratorId){
        return adminService.getArbitratorById(arbitratorId);
    }
    @GetMapping("/admin/arbitrator")
    public List<ArbitratorResponse> getAllArbitrators(@RequestParam(required = false) Integer pageNumber
            , @PageableDefault(size = 10,page=0,sort = "id") Pageable pageable){
        if (pageNumber != null) {
            pageable = PageRequest.of(pageNumber, pageable.getPageSize(), pageable.getSort());
        }
        return adminService.getArbitratorList(pageable);

    }
    @GetMapping("/admin/case/{caseId}")
    public AdminCaseResponse getCaseById(@PathVariable Integer caseId){
        return adminService.getCaseById(caseId);
    }


    @GetMapping("/admin/case")
    public List<CaseResponse> getAllCases(@RequestParam(required = false) Integer pageNumber
            , @PageableDefault(size = 10,page=0,sort = "id") Pageable pageable){
        if (pageNumber != null) {
            pageable = PageRequest.of(pageNumber, pageable.getPageSize(), pageable.getSort());
        }
        return   adminService.getCaseList(pageable);
    }


    @GetMapping("/admin/borrower/{borrowerId}")
    public BorrowerResponse getBorrowerById(@PathVariable Integer borrowerId ){
    return adminService.getBorrowerById(borrowerId);
    }
    @GetMapping("/admin/borrower")
    public List<BorrowerResponse> getAllBorrowers(){
        return adminService.getBorrwerList();
    }

    @GetMapping("/admin/bank")
    public List<BankResponse> getAllBanks(@RequestParam(required = false) Integer pageNumber
            , @PageableDefault(size = 10,page=0,sort = "id") Pageable pageable){
        if (pageNumber != null) {
            pageable = PageRequest.of(pageNumber, pageable.getPageSize(), pageable.getSort());
        }
        return adminService.getBanksList(pageable);
    }
}
