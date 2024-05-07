package com.resolute.zero.controllers;

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


@RestController
@Slf4j
@CrossOrigin("*")
public class MediaController {

    @Autowired
    private CodeComponent codeComponent;

    @Autowired
    private MediaService mediaService;

    @PostMapping("/media")
    public ResponseEntity<?> singleUploading(@RequestParam("file") MultipartFile file, @RequestParam String mainType,@RequestParam String subType,@RequestParam Integer caseId) throws IOException {
        String code = codeComponent.getCode(mainType,subType,caseId);
        mediaService.uploadFile(code,file);
        return ResponseEntity.ok(code +" file uploaded successfully");
    }

    @PostMapping("/media/bulk")
    public ResponseEntity<?> bulkUploading(@RequestParam("files") MultipartFile[] files) throws IOException {
        var list = mediaService.uploadFiles(files);
        return ResponseEntity.ok("file uploaded with names "+list);
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
