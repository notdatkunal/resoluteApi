package com.resolute.zero.responses;


import lombok.Builder;
import lombok.Data;


import java.util.Date;

@Builder
@Data
public class DocumentResponse {

    Integer caseId;
    Integer documentId;
    String subType;
    String mainType;
    String documentTitle;
    Date uploadDate;


}

