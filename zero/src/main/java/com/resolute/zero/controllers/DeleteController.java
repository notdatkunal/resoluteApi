package com.resolute.zero.controllers;

import com.resolute.zero.services.DeleteService;
import com.resolute.zero.services.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
public class DeleteController {
    @Autowired
    private DeleteService service;
    @Autowired
    private MediaService mediaService;

    @DeleteMapping("/admin/deleteDocument/{documentId}")
    public ResponseEntity<?> deleteDocument(@PathVariable Integer documentId){
        return mediaService.deleteDocument(documentId);
    }
    @DeleteMapping("/admin/deleteCase/{caseId}")
    public void deleteCase(@PathVariable Integer caseId){
        service.deleteCase(caseId);
    }
    @DeleteMapping("/admin/deleteBank/{bankId}")
    public void deleteBank(@PathVariable Integer bankId){
        service.deleteBank(bankId);
    }
}
