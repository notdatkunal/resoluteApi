package com.resolute.zero.responses;

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
