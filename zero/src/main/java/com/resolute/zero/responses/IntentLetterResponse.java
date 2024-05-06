package com.resolute.zero.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IntentLetterResponse {
    String notice;
    String RPAD;
    String tracking;
}
