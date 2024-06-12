package com.resolute.zero.requests;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Builder
public class AdminOrderRequest {
   String type;
   Date date;
   MultipartFile file;
   Integer caseId;
}
