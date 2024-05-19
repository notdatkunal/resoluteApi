package com.resolute.zero.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Builder
@Data
public class DocumentResponse {

    Integer caseId;
    String subType;
    String mainType;
    String documentTitle;
    Date uploadDate;


}

