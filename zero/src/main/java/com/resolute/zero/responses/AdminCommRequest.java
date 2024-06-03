package com.resolute.zero.responses;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Builder
public class AdminCommRequest {
    private Integer caseId;
    private MultipartFile file;
    private Date date;
}
