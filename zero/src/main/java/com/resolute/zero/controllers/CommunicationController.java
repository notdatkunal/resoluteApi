package com.resolute.zero.controllers;

import com.resolute.zero.responses.AdminCommRequest;
import com.resolute.zero.responses.CommunicationResponse;
import com.resolute.zero.services.CaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class CommunicationController {
    @Autowired
    private final CaseService caseService;
    @PostMapping("/admin/comm")
    public ResponseEntity<?> createComm(@ModelAttribute AdminCommRequest adminCommRequest) throws IOException {
        return caseService.createComm(adminCommRequest);
    }

    @GetMapping({"/admin/comm/{caseId}","/case/communication/{caseId}"})
    public List<CommunicationResponse> getCommunicationByCaseId(@PathVariable Integer caseId){
        return caseService.getCommunicationByCaseId(caseId);
    }
}
