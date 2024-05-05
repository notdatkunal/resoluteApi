package com.resolute.zero.responses;

import com.resolute.zero.configurations.SampleDocuments;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;

import java.util.Date;

@Builder
@Data
public class DocumentResponse {
    String documentByteString;
    String type;
    String documentTitle;
    Date uploadDate;

}

