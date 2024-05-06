package com.resolute.zero.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AwardLetterResponse {
    String notice;
    String RPAD;
    String tracking;
    String bankDocument;
    String roznama;

}
