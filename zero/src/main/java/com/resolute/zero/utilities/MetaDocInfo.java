package com.resolute.zero.utilities;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class MetaDocInfo {

    String mainType;
    String mainTypeAbbr;
    String subType;
    String subTypeAbbr;
    String code;
    MultipartFile file;
    Integer caseId;
    Integer hearingId;
    String fileExtension;
    String fileName;
    private static final CodeComponent codeComponent = new CodeComponent();


}
