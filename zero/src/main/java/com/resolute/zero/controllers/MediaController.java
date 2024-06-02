package com.resolute.zero.controllers;

import com.resolute.zero.domains.Document;
import com.resolute.zero.services.MediaService;
import com.resolute.zero.utilities.CodeComponent;
import com.resolute.zero.utilities.MetaDocInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Collection;
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
            , @RequestParam(required = false) Integer hearingId
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



    @GetMapping("/list/mainTypes")
    public Collection<String> getDocmentTypes()
    {
        return MetaDocInfo.MAIN_TYPE_MAP.keySet();
    }
    @GetMapping("/list/subTypes")
    public Collection<String> getDocumentSubTypes(){
        return MetaDocInfo.SUB_TYPE_MAP.keySet();
    }


}
