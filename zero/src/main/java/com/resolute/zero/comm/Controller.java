package com.resolute.zero.comm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class Controller {

    @Autowired
    private final CommService service;
    @PostMapping("/admin/send")
    public void sendAPIResolute(CommunicationRequest communicationRequest){
        log.info("this is request {} ",communicationRequest);
        service.sendAPIResolute(communicationRequest);
    }
}
