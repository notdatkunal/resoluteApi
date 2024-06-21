package com.resolute.zero.whatsapp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class WhController {
    @PostMapping("/sendWhatsapp/{number}")
    public void sendWhatsapp(@PathVariable String number){
        whService.sendHelloWorldMessage("91"+number);
    }
    @Autowired
    private WhService whService;
}
