package com.resolute.zero.controllers;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CommDateResponse {

    Date date;
    String whatsAppComm;
    String whatsAppCommTitle;
    String emailComm;
    String emailCommTitle;
    String textComm;
    String textCommTitle;

}
