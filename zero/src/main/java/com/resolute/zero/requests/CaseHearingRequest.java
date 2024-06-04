package com.resolute.zero.requests;

import lombok.*;

import java.util.Date;



    @Builder
    @Data
    public class CaseHearingRequest {

        private Date hearingDate;

        String minutesOfMeetings;
        String meetingRecordings;

        Boolean current;


    }
