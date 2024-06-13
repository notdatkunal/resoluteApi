package com.resolute.zero.controllers;

import com.resolute.zero.exceptions.AppException;
import com.resolute.zero.requests.AdminCaseRequest;
import com.resolute.zero.services.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class BulkCaseUploadsController {

    @Autowired
    private MediaService mediaService;

    @PostMapping("/admin/case/bulk")
    public List<AdminCaseRequest> bulkCaseUploads(@ModelAttribute MultipartFile sheet){
        if(!Objects.requireNonNull(sheet.getOriginalFilename()).contains("xlsx"))
            throw AppException.builder()
                    .data(ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,"this file format not allowed")).build())
                    .build();


        return mediaService.saveCases(sheet);
    }

}
