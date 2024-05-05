package com.resolute.zero.requests;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Builder
public class DocumentRequest {

    MultipartFile documentFile;
    String type;
    String documentTitle;
    Integer caseId;
    Integer bankId;
}
