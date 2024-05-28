package com.resolute.zero.services;


import com.resolute.zero.domains.Document;
import com.resolute.zero.repositories.CaseRepository;
import com.resolute.zero.utilities.CodeComponent;
import com.resolute.zero.utilities.MetaDocInfo;
import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Service
public class MediaService {
    public String uploadFile(String code, MultipartFile file) throws IOException {
        var name = file.getOriginalFilename();
        if (name == null) throw new AssertionError();
        String format = name.split("\\.")[1];
        Files.createDirectories(Path.of("media"));
        Path uploadTo = Path.of(String.format("media/%s.%s", code,format));
        file.transferTo(uploadTo);
        return code;
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
    private CodeComponent codeComponent;
    @Autowired
    private CaseRepository caseRepository;
    public void saveDocumentInDB(MetaDocInfo metaDocInfo) {
        Document document = new Document();

        document.setDocumentMainTypeTitle(metaDocInfo.getMainType());
        document.setDocumentSubTypeTitle(metaDocInfo.getSubType());
        document.setImageName(codeComponent.getCode(metaDocInfo.getMainType(),metaDocInfo.getSubType(),metaDocInfo.getCaseId()));
        document.setFileExtension(metaDocInfo.getFileExtension());
        var caseOptional = caseRepository.findById(metaDocInfo.getCaseId());

        if(caseOptional.isPresent()){
            var caseObj = caseOptional.get();
            var documentsList = caseObj.getDocumentList();
            documentsList.add(document);
            caseObj.setDocumentList(documentsList);
            caseRepository.save(caseObj);
        }else {
            throw new RuntimeException("Case Does Not exist!!");

        }



    }

    public ResponseEntity<String> getFile(String fileName) throws IOException {
        // Path to the media folder (replace with your actual path)
        String mediaPath = "/media/" + fileName;
//        String mediaPath = "C:\\Users\\kunal\\Downloads\\quarkus-web-interface\\resoluteApi\\zero\\media\\" + fileName;

        // Read the file as bytes
        var fileInputStream = new FileInputStream(mediaPath);
        byte[] fileBytes = IOUtils.toByteArray(fileInputStream);
        String mimeType = "";
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
}
