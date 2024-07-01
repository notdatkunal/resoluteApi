package com.resolute.zero.services;

import com.resolute.zero.domains.Communication;
import com.resolute.zero.helpers.Helper;
import com.resolute.zero.repositories.CaseRepository;
import com.resolute.zero.repositories.CommunicationRepository;
import com.resolute.zero.requests.AdminCommRequest;
import com.resolute.zero.responses.CommunicationResponse;
import com.resolute.zero.utilities.CodeComponent;
import com.resolute.zero.utilities.MetaDocInfo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class CommunicationService {
    private CaseRepository caseRepository;
    public List<CommunicationResponse> getCommunicationByCaseId(Integer id) {
        return CaseService
                .extracted(caseRepository,id)
                .getCommunications()
                .stream()
                .map(Helper.Convert::convertCommunicationResponse)
                .toList();
    }
    @Autowired
    private CodeComponent codeComponent;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private CommunicationRepository communicationRepository;
    public ResponseEntity<?> createComm(AdminCommRequest adminCommRequest) throws IOException {
        var obj = CaseService.extracted(caseRepository,adminCommRequest.getCaseId());
        obj.setCommunicationCount(obj.getCommunicationCount()+1);
        Communication communication = Helper.Creator.createCommunication(adminCommRequest);
        {	//creating comm sequence
            if (obj.getCommunicationCount() < 10)
                communication.setSequence("C0" + obj.getCommunicationCount());
            else
                communication.setSequence("C" + obj.getCommunicationCount());
        }

        MetaDocInfo metaDocInfo = codeComponent.getMetaCode("communication", communication.getSequence(), adminCommRequest.getCaseId(), 0, adminCommRequest.getFile());
        mediaService.uploadFile(metaDocInfo);
        mediaService.saveDocumentInDB(metaDocInfo);
        communication.setFileName(metaDocInfo.getFileName());
        communicationRepository.save(communication);
        obj.getCommunications().add(communication);
        caseRepository.save(obj);
        return ResponseEntity
                .of(ProblemDetail
                        .forStatus(HttpStatus.CREATED))
                .eTag("communication created successfully")
                .build();
    }


}
