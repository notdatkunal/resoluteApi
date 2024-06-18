package com.resolute.zero.controllers;

import com.resolute.zero.domains.Template;
import com.resolute.zero.helpers.TemplateType;
import com.resolute.zero.services.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class BulkCommunicationController {

    @Autowired
    private TemplateService templateService;

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

}
