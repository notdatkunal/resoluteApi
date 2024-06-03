package com.resolute.zero.responses;

import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
@Builder
public class CommunicationResponse {
    private String fileName;
    private Date date;


}
