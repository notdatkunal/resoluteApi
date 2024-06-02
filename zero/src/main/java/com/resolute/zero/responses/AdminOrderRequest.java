package com.resolute.zero.responses;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

@Data
@Builder
public class AdminOrderRequest {
   String type;
   Date date;
   MultipartFile file;
   Integer caseId;
}
