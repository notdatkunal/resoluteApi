package com.resolute.zero.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommunicationResponse {
    List<CommDateResponse> dates;
}
