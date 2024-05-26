package com.resolute.zero.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Builder
@Getter
@Setter
public class CaseHearingRequest {
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy/mm/dd")
    Date hearingDate;

}
