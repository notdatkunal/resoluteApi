package com.resolute.zero.services;

import com.resolute.zero.domains.CaseType;
import com.resolute.zero.domains.CaseTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TypeService {
    private final CaseTypeRepository caseTypeRepository;

    public void addCase(String type){
        CaseType typeObj = new CaseType();
        typeObj.setType(type);
        caseTypeRepository.save(typeObj);
    }
    public List<String> listCase(){
        return caseTypeRepository.findAll().stream().map(CaseType::getType).toList();
    }
}
