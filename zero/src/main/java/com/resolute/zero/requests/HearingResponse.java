package com.resolute.zero.requests;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class HearingResponse {
    Date hearingDate;
    String minutesOfMeetings;
    Integer hearingId;
    String meetingRecordings;
    Boolean current;
}
