package com.resolute.zero.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Builder
@Data
public class CaseHearingRequest {

    private Date hearingDate;

}
