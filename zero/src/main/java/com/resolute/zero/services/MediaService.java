package com.resolute.zero.services;


import com.resolute.zero.domains.Document;
import com.resolute.zero.repositories.CaseRepository;
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
}
