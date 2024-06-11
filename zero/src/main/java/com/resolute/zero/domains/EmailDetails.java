package com.resolute.zero.domains;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDetails {
    private String to;
    private String from;
    private String cc;
    private String bcc;
    private String subject;
    private String body;
}
