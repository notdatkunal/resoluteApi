package com.resolute.zero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    public boolean sendEmail(EmailDetails emailDetails) throws Exception {
        emailDetails.setFrom("jmswiftapp@gmail.com");
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailDetails.getFrom());
            message.setTo(emailDetails.getTo());
            message.setText(emailDetails.getBody());
            message.setSubject(emailDetails.getSubject());
            mailSender.send(message);
            System.out.println("Mail Send...");
            return true;
        }catch (Exception e ) {
            throw new Exception("Exception Occured in email service, Error: "+ e.getMessage());
        }
    }

}
