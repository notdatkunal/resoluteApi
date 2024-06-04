package com.resolute.zero.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CaseUpdateHearingRequest {
    String date;
}
