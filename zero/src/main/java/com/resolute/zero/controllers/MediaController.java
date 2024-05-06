package com.resolute.zero.controllers;

import com.resolute.zero.utilities.CodeComponent;
import com.resolute.zero.utilities.MetaDocInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
public class MediaController {

    @Autowired
    private CodeComponent codeComponent;

//@RequestMapping("/media")
    @PostMapping("/media/bulk")
    public ResponseEntity<?> bulkUploading(@RequestParam("files") MultipartFile[] files){
        var list = new ArrayList<String>();
        for (MultipartFile file : files){
            log.info(file.getOriginalFilename());
            list.add(file.getOriginalFilename());
        }

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
