package com.resolute.zero.requests;

import lombok.Builder;
import lombok.Data;


import java.util.Date;


@Builder
@Data
public class SearchRequest {

    String searchParameter;
    Date date;
}
