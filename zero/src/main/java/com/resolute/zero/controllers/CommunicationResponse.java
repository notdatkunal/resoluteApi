package com.resolute.zero.controllers;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommunicationResponse {
    List<CommDateResponse> dates;
}
