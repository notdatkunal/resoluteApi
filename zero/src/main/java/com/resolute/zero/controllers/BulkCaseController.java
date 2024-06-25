package com.resolute.zero.controllers;

import com.resolute.zero.excel.ExcelService;
import com.resolute.zero.exceptions.AppException;
import com.resolute.zero.requests.AdminCaseRequest;
import com.resolute.zero.services.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class BulkCaseController {

    @Autowired
    private MediaService mediaService;
    @PutMapping("/admin/case/bulk")
    public List<AdminCaseRequest> bulkCaseUpdate(@ModelAttribute MultipartFile sheet){
        if(!Objects.requireNonNull(sheet.getOriginalFilename()).contains("xlsx"))
            throw AppException.builder()
                    .data(ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,"this file format not allowed")).build())
                    .build();


        List<AdminCaseRequest> list = mediaService.updateCases(sheet);
        if(list.isEmpty()){
            throw AppException.builder()
                    .data(ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,"problem in sheet or sheet is empty")).build())
                    .build();
        }
        return list;
    }

    @PostMapping("/admin/case/bulk")
    public List<AdminCaseRequest> bulkCaseUploads(@ModelAttribute MultipartFile sheet){
        if(!Objects.requireNonNull(sheet.getOriginalFilename()).contains("xlsx"))
            throw AppException.builder()
                    .data(ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,"this file format not allowed")).build())
                    .build();


        var list = mediaService.saveCases(sheet);
        if(list.isEmpty()){
            throw AppException.builder()
                    .data(ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,"problem in sheet or sheet is empty")).build())
                    .build();
        }
        return list;
    }
    @GetMapping("/getsheet/{bankId}")
    public ResponseEntity<Resource> download(@PathVariable Integer bankId) throws IOException {
        var data =  excelService.getExcelDataByBankId(bankId);
        var actualData = new InputStreamResource(data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=bank")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(actualData)
                ;
    }

    @Autowired
    private ExcelService excelService;

}
