package com.resolute.zero.responses;


import jakarta.persistence.EntityResult;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data

public class CaseStatusResponse {
    Date firstHearingDate;
    Date NextHearingDate;
    String caseStage;
    String arbitratorName;

}
