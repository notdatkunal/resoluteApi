package com.resolute.zero.requests;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class CommunicationRequest {
    String sendTo;
    String platform;
    String template;
    Map<Integer,String> arguments;
}
