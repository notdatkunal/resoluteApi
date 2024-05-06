package com.resolute.zero.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReferenceLetterResponse {

    String notice;
    String RPAD;
    String tracking;
}
