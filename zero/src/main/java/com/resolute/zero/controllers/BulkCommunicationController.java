package com.resolute.zero.controllers;

import com.resolute.zero.domains.Template;
import com.resolute.zero.exceptions.AppException;
import com.resolute.zero.helpers.TemplateType;
import com.resolute.zero.services.EmailService;
import com.resolute.zero.services.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class BulkCommunicationController {

    @Autowired
    private TemplateService templateService;
    @Autowired
    private EmailService emailService;

    @PutMapping("/template/email")
    public void updateEmailTemplate(@RequestBody Template template){
        templateService.updateEmailTemplate(template);
    }
    @PutMapping("/template/whatsapp")
    public void updateWhatsappTemplate(@RequestBody Template template){
       templateService.updateWhatsappTemplate(template);
    }
    @GetMapping("/template/whatsapp")
    public Template getWhatsappTemplate(){
        return templateService.getWhatsappTemplate();
    }
    @GetMapping("/template/email")
    public Template getEmailTemplate(){
        return templateService.getEmailTemplate();
    }

    @PostMapping("/bulk/whatsapp")
    public void sendBulkWhatsapp(@ModelAttribute MultipartFile sheet){
        if(!Objects.requireNonNull(sheet.getOriginalFilename()).contains("xlsx"))
            throw AppException.builder()
                    .data(ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,"this file format not allowed")).build())
                    .build();
        emailService.sendBulkWhatsapp(sheet);
    }

    @PostMapping("/bulk/email")
    public void sendBulkEmail(@ModelAttribute MultipartFile sheet){
        if(!Objects.requireNonNull(sheet.getOriginalFilename()).contains("xlsx"))
            throw AppException.builder()
                    .data(ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,"this file format not allowed")).build())
                    .build();
        emailService.sendBulkEmail(sheet);
    }

}
