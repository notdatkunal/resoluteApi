package com.resolute.zero.controllers;

import com.resolute.zero.domains.Document;
import com.resolute.zero.responses.DocumentResponse;
import com.resolute.zero.services.AdminService;
import com.resolute.zero.services.MediaService;
import com.resolute.zero.utilities.CodeComponent;
import com.resolute.zero.utilities.MetaDocInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@RestController
@Slf4j
@CrossOrigin("*")
public class MediaController {

    @Autowired
    private CodeComponent codeComponent;

    @Autowired
    private MediaService mediaService;

    @GetMapping("/media/{fileName}")
    public ResponseEntity<String> getFile(@PathVariable String fileName) throws IOException {
        return mediaService.getFile(fileName);
    }
    @GetMapping("/docs/{caseId}")
    public List<Document> getDocsByCaseId(@PathVariable Integer caseId){
        return mediaService.getDocsByCaseId(caseId);
    }

    @PostMapping("/media")
    public ResponseEntity<?> singleUploading(
            @RequestAttribute("file") MultipartFile file
            , @RequestParam String mainType
            , @RequestParam String subType
            , @RequestParam Integer caseId
            , @RequestParam(required = false,defaultValue = "0") Integer hearingId
    ) throws IOException
    {
        MetaDocInfo metaDocInfo = codeComponent.getMetaCode(mainType,subType,caseId,hearingId,file);
        mediaService.uploadFile(metaDocInfo);
        return mediaService.saveDocumentInDB(metaDocInfo);
    }

    @PostMapping("/media/bulk")
    public ResponseEntity<?> bulkUploading(@RequestParam("files") MultipartFile[] files) throws IOException {
        var fileNamesList = mediaService.uploadFiles(files);
        mediaService.saveDocumentsInDB(codeComponent.getMetaDocsInfo(fileNamesList));
        return ResponseEntity.ok("file uploaded with names "+fileNamesList);
    }
    @Autowired
    private AdminService adminService;
    /**
     * please read the service documentation before using this
     * */
    @GetMapping("/admin/documents")
    public List<DocumentResponse> listDocuments(){
        return adminService.getDocumentList();
    }

    @DeleteMapping("/admin/deleteDocument/{documentId}")
    public ResponseEntity<?> deleteDocument(@PathVariable Integer documentId){
       return mediaService.deleteDocument(documentId);
    }



}
