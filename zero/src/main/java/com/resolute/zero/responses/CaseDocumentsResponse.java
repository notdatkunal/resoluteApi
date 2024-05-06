package com.resolute.zero.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CaseDocumentsResponse {
    LoanRecallNoticeResponse loanRecallNotice;
    IntentLetterResponse intentLetter;
    ReferenceLetterResponse referenceLetter;
    ConcentLetterResponse concentLetter;
    IntimationLetterResponse intimationLetter;
    AwardLetterResponse awardLetter;

}
