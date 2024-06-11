package com.resolute.zero.controllers;

import com.resolute.zero.requests.*;
import com.resolute.zero.services.CaseService;
import com.resolute.zero.utilities.ApplicationUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class HearingController {

    @Autowired
    private CaseService caseService;

    @DeleteMapping("/admin/hearing/{hearingId}")
    public void  deleteHearingByCaseId(@PathVariable Integer hearingId){
        caseService.deleteHearingById(hearingId);
    }


    @GetMapping("/admin/hearing/{hearingId}")
    public HearingResponse getHearingDateByHearingId(@PathVariable Integer hearingId){
        return caseService.getHearingDateByHearingId(hearingId);
    }
    @GetMapping({"/admin/hearings/{caseId}","/case/hearings/{caseId}"})
    public List<HearingResponse> getProceedings(@PathVariable Integer caseId){
        return caseService.getHearingsByCaseId(caseId);
    }
    @PostMapping("/admin/hearing/{caseId}")
    public void  createHearingByCaseId(@PathVariable Integer caseId, @RequestBody CaseHearingRequest caseHearingRequest){
        caseService.createHearingByCaseId(caseId,CaseHearingRequest.builder().hearingDate(caseHearingRequest.getHearingDate()).build());
    }
    @PutMapping("/admin/hearing/{hearingId}")
    public void  updateHearingByCaseId(@RequestParam String date, @PathVariable Integer hearingId) throws ParseException {
        caseService.updateHearingByHearingId(hearingId, ApplicationUtility.parseDate(date));
    }

}
