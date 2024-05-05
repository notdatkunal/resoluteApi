package com.resolute.zero.responses;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CaseHistoryResponse {
    CaseDetailsResponse caseDetails;
    CaseStatusResponse caseStatus;
}
