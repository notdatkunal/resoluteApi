package com.resolute.zero.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileUploadRequest {
    Integer caseId;
    String mainType;
    String subType;

}
