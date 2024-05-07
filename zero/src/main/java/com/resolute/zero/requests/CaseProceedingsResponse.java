package com.resolute.zero.requests;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CaseProceedingsResponse {
    private List<HearingResponse> hearings;
}
