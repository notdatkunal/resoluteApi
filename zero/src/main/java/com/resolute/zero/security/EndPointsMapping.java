package com.resolute.zero.security;

import com.resolute.zero.domains.Endpoint;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EndPointsMapping {

    private List<Endpoint> endpointList;

}
