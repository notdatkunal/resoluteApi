package com.resolute.zero.services;


import com.resolute.zero.domains.Document;
import com.resolute.zero.exceptions.AppException;
import com.resolute.zero.repositories.CaseRepository;
import com.resolute.zero.repositories.DocumentRepository;
import com.resolute.zero.repositories.ProceedingRepository;
import com.resolute.zero.utilities.MediaUtility;
import com.resolute.zero.utilities.MetaDocInfo;
import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Service
public class MediaService {
    public void uploadFile(MetaDocInfo metaDocInfo) throws IOException {
        var name = metaDocInfo.getFile().getOriginalFilename();
        if (name == null) throw new AssertionError();
        String format = name.split("\\.")[1];
        Files.createDirectories(Path.of("media"));
        Path uploadTo = Path.of(String.format("media/%s.%s", metaDocInfo.getCode(),format));
        metaDocInfo.getFile().transferTo(uploadTo);
        metaDocInfo.setFileName(uploadTo.getFileName().toString());
    }
    public ArrayList<String> uploadFiles(MultipartFile[] files) throws IOException {
        Files.createDirectories(Path.of("media"));
        var list = new ArrayList<String>();
        for (MultipartFile file : files){
            list.add(saveSingleFile(file));
        }
        return list;
    }

    private String saveSingleFile(MultipartFile file) throws IOException {
        Path uploadTo = Path.of(String.format("media/%s", file.getOriginalFilename()));
        file.transferTo(uploadTo);
        return file.getOriginalFilename();
    }

    public void saveDocumentsInDB(List<MetaDocInfo> metaDocsInfo) {

        for(MetaDocInfo metaDocInfo:metaDocsInfo)
        {
            this.saveDocumentInDB(metaDocInfo);

        }
    }

    @Autowired
    private CaseRepository caseRepository;
    public ResponseEntity<?> saveDocumentInDB(MetaDocInfo metaDocInfo) {
        System.out.println(metaDocInfo);
        Document document = new Document();
        document.setDocumentMainTypeTitle(metaDocInfo.getMainType());
        document.setDocumentSubTypeTitle(metaDocInfo.getSubType());
        document.setCaseId(metaDocInfo.getCaseId());
        document.setImageName(metaDocInfo.getFileName());
        document.setFileExtension(metaDocInfo.getFileExtension());
        var caseOptional = caseRepository.findById(metaDocInfo.getCaseId());
        if(caseOptional.isPresent()){
            var caseObj = caseOptional.get();
            var documentsList = caseObj.getDocumentList();
            documentsList.add(document);
            caseObj.setDocumentList(documentsList);
            caseRepository.save(caseObj);
        }else {
             return ResponseEntity.notFound()
                     .eTag("case id not found")
                     .build();
        }
        if(metaDocInfo.getHearingId() !=0){
            var proceedingOpt = proceedingRepository.findById(metaDocInfo.getHearingId());
            if(proceedingOpt.isEmpty())
                throw AppException.builder()
                    .data(ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE,"proceeding not found")).build())
                    .build();
            var proceeding = proceedingOpt.get();
            if (metaDocInfo.getMainType().equals("recording")) {
                proceeding.setMeetingRecordings(metaDocInfo.getFileName());
            } else {
                proceeding.setMinutesOfMeetings(metaDocInfo.getFileName());
            }
            proceedingRepository.save(proceeding);
        }
        return ResponseEntity.ok(metaDocInfo.getFileName()+" saved in server ");

    }

    public ResponseEntity<String> getFile(String fileName) throws IOException {
        // Path to the media folder (replace with your actual path)
                String mediaPath= MediaUtility.getPath(fileName);
        // Read the file as bytes
        var fileInputStream = new FileInputStream(mediaPath);
        byte[] fileBytes = IOUtils.toByteArray(fileInputStream);
        String mimeType;
//        String mimeType = "";
        Tika tika = new Tika();
        try {
            // Get MIME type
            var file = new File(mediaPath);
            mimeType = tika.detect(file);

        } catch (IOException e) {
            e.printStackTrace();
            mimeType = "application/octet-stream";
        }
//        String format = fileName.split("\\.")[1];
        String bytes = Base64.getEncoder().encodeToString(fileBytes);
        // Convert bytes to Base64 string
        String base64Encoded = "data:"+mimeType+";base64,"+bytes;

        // Prepare and return the response
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(base64Encoded);
    }

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private ProceedingRepository proceedingRepository;

    public List<Document> getDocsByCaseId(Integer caseId) {
       return documentRepository.findByCaseId(caseId);
    }
}
