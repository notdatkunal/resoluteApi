package com.resolute.zero.services;

import com.resolute.zero.models.CaseDocument;
import com.resolute.zero.models.Document;
import com.resolute.zero.repositories.CaseDocumentRepository;
import com.resolute.zero.utilities.CodeComponent;
import com.resolute.zero.utilities.MetaDocInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class MediaService {
    public String uploadFile(String code, MultipartFile file) throws IOException {
        String format = file.getOriginalFilename().split("\\.")[1];
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
    private CaseDocumentRepository caseDocumentRepository;
    public void saveDocumentInDB(MetaDocInfo metaDocInfo) {
        Document document = new Document();

        document.setDocumentMainTypeTitle(metaDocInfo.getMainType());
        document.setDocumentSubTypeTitle(metaDocInfo.getSubType());
        document.setImageName(codeComponent.getCode(metaDocInfo.getMainType(),metaDocInfo.getSubType(),metaDocInfo.getCaseId()));

        var documentOptional = caseDocumentRepository.findCaseDocumentByCaseId(metaDocInfo.getCaseId());

        if(documentOptional.isPresent()){

            var caseDocument = documentOptional.get();

            var documentsList = caseDocument.getDocumentList();
            documentsList.add(document);
            caseDocument.setDocumentList(documentsList);
            caseDocumentRepository.save(caseDocument);


        }else {

            CaseDocument caseDocument = new CaseDocument();

            caseDocument.setCaseId(metaDocInfo.getCaseId());
            var documentList = caseDocument.getDocumentList();
            documentList.add(document);
            caseDocument.setDocumentList(documentList);
            caseDocumentRepository.save(caseDocument);

        }



    }
}
