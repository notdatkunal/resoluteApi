package com.resolute.zero.controllers;



import com.resolute.zero.responses.*;
import com.resolute.zero.services.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
public class CaseController {

    @Autowired
    private CaseService caseService;

    @GetMapping("/case/history/{caseId}")
    public CaseHistoryResponse getCaseHistory(@PathVariable Integer caseId){
        return caseService.getCaseHistoryByCaseId(caseId);
    }


}
