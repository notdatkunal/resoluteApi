package com.resolute.zero.controllers;



import com.resolute.zero.requests.*;
import com.resolute.zero.responses.*;
import com.resolute.zero.services.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
        adminService.saveCase(request);
    }

    @PostMapping("/borrower")
    public void addBorrower(@RequestBody BorrowerRequest request, HttpSession session){

        adminService.addBorrower(request);

    }

    @PutMapping("/arbitrator/{arbitratorId}")
    public void updateArbitrator(@RequestBody ArbitratorRequest request,@PathVariable Integer arbitratorId){

        adminService.updateArbitrator(request,arbitratorId);
    }

    @PostMapping("/arbitrator")
    public void addArbitrator(@RequestBody ArbitratorRequest request, HttpSession session){

        adminService.addArbitrator(request);
    }

    @PostMapping("/bank")
    public void addBank(@RequestBody BankRequest request, HttpSession session){
        adminService.addBank(request);
    }
    @PutMapping("/bank/{bankId}")
    public void updateBank(@RequestBody BankRequest request,@PathVariable Integer bankId) {
    	adminService.updateBank(request,bankId);
    }

    /**
     * please read the service documentation before using this
     * */
    @GetMapping("/documents")
    public List<DocumentResponse> listDocuments(
            HttpSession session){

        return adminService.getDocumentList();

    }

    @GetMapping("/arbitrator/{arbitratorId}")
    public ArbitratorResponse getArbitratorById(@PathVariable Integer arbitratorId){

        return adminService.getArbitratorById(arbitratorId);
    }
    @GetMapping("/arbitrator")
    public List<ArbitratorResponse> getAllArbitrators(HttpSession session){

        return adminService.getArbitratorList();

    }




    @GetMapping("/case/{caseId}")
    public CaseResponse getCaseById(@PathVariable Integer caseId, HttpSession session){

        return adminService.getCaseById(caseId);
    }

    @GetMapping("/search/case")
    public List<CaseResponse> searchCase(@RequestParam(value = "parameter",required = false) String searchParameter,@RequestParam(value = "date",required = false) Date date, HttpSession session) {

    return adminService.getSearchResponse(searchParameter,date);
    }
    @GetMapping("/case")
    public List<CaseResponse> getAllCases(HttpSession session){
        return   adminService.getCaseList();
    }


    @GetMapping("/borrower/{borrowerId}")
    public BorrowerResponse getBorrowerById(@PathVariable Integer borrowerId, HttpSession session){
    return adminService.getBorrowerById(borrowerId);
    }
    @GetMapping("/borrower")
    public List<BorrowerResponse> getAllBorrowers(){
        return adminService.getBorrwerList();

    }









    
    @GetMapping("/bank/{bankId}")
    public BankResponse getBankById(@PathVariable Integer bankId, HttpSession session){
        return adminService.getBankById(bankId);
    }
    @GetMapping("/bank")
    public List<BankResponse> getAllBanks( HttpSession session){
        return adminService.getBanksList();

    }

}
