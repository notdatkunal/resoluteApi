package com.resolute.zero.controllers;

import com.resolute.zero.domains.EmailDetails;
import com.resolute.zero.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class EmailController {


    @Autowired
    public EmailService emailService;
    @PostMapping("/sendMail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDetails emailDetails){
        try {
            boolean result = emailService.sendEmail(emailDetails);
            if(result)
                return new ResponseEntity<>("email send", HttpStatus.OK);
            else
                return new ResponseEntity<>("something wents wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("something wents wrong, e:"+ e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
