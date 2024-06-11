package com.resolute.zero.controllers;

import com.resolute.zero.responses.CaseResponse;
import com.resolute.zero.services.AdminService;
import com.resolute.zero.utilities.ApplicationUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class SearchController {
    @Autowired
    private final AdminService adminService;

    @GetMapping("/admin/search/case")
    public List<CaseResponse> searchCase(@RequestParam(value = "parameter",required = false) String searchParameter, @RequestParam(value = "date",required = false) String date) throws ParseException {
        log.info("the date is : {} ", date.toString());
        return adminService.getSearchResponse(searchParameter, ApplicationUtility.parseDate(date));
    }

    @GetMapping("/admin/search/caseByArbitrator/{arbitratorId}")
    public List<CaseResponse> searchCaseByArbitrator(@PathVariable Integer arbitratorId, @RequestParam(value = "parameter",required = false) String searchParameter, @RequestParam(value = "date",required = false) String date) throws ParseException {
        log.info("the date is {} ", date.toString());
        return  adminService.getSearchResponseArbitratorId(searchParameter,ApplicationUtility.parseDate(date),arbitratorId);
    }
    @GetMapping("/admin/search/caseByBank/{bankId}")
    public List<CaseResponse> searchCaseByBank(@PathVariable Integer bankId, @RequestParam(value = "parameter",required = false) String searchParameter, @RequestParam(value = "date",required = false) String date) throws ParseException {
        log.info("the date is {} ", date.toString());
        return  adminService.getSearchResponseBankId(searchParameter,ApplicationUtility.parseDate(date),bankId);
    }

}
