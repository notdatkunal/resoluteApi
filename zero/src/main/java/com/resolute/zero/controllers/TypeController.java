package com.resolute.zero.controllers;

import com.resolute.zero.utilities.TypeMappingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
@RestController
@Slf4j
@CrossOrigin("*")
public class TypeController {


    @GetMapping("/list/mainTypes")
    public Collection<String> getDocmentTypes()
    {
        return TypeMappingUtil.MAIN_TYPE_MAP.keySet();
    }
    @GetMapping("/list/subTypes")
    public Collection<String> getDocumentSubTypes(){
        return TypeMappingUtil.SUB_TYPE_MAP.keySet();
    }
    @GetMapping("/list/caseTypes")
    public Collection<String> getCaseType(){
        return TypeMappingUtil.CASE_TYPES;
    }
}
