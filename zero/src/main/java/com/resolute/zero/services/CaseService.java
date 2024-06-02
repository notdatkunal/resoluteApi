package com.resolute.zero.services;



import com.resolute.zero.domains.CaseOrder;
import com.resolute.zero.exceptions.AppException;
import com.resolute.zero.repositories.CaseOrderRepository;
import com.resolute.zero.requests.CaseHearingRequest;
import com.resolute.zero.domains.Proceeding;
import com.resolute.zero.repositories.ProceedingRepository;
import com.resolute.zero.requests.HearingResponse;
import com.resolute.zero.responses.AdminOrderRequest;
import com.resolute.zero.responses.CaseDocumentsResponse;
import com.resolute.zero.responses.CommunicationResponse;
import com.resolute.zero.utilities.CodeComponent;
import com.resolute.zero.utilities.MetaDocInfo;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.resolute.zero.domains.BankCase;

import com.resolute.zero.helpers.Helper;
import com.resolute.zero.repositories.CaseRepository;
import com.resolute.zero.responses.CaseHistoryResponse;

import java.io.IOException;
import java.util.Date;
import java.util.List;



@RequiredArgsConstructor
@Service
public class CaseService {
	
	
	@Autowired
	private final CaseRepository caseRepository;
	@Autowired
	private final ProceedingRepository proceedingRepository;
	private final CaseOrderRepository caseOrderRepository;

	public CaseHistoryResponse getCaseHistoryByCaseId(Integer caseId) {
		var obj = CaseService.extracted(caseRepository,caseId);
	    return Helper.Convert.convertCaseHistoryResponse(obj);
	}

	
	private static BankCase extracted(CaseRepository repository,Integer caseId) {
		var caseOptional = repository.findById(caseId);
		if(caseOptional.isEmpty()) throw AppException.builder().data(ResponseEntity.of(ProblemDetail
						.forStatusAndDetail(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE,"case id does not exist in database"))
				.build()).build();
		return caseOptional.get();
	}

	public CaseDocumentsResponse getCaseDocuments(Integer caseId) {
		var obj = CaseService.extracted(caseRepository,caseId);
		return Helper.Convert.convertCaseDocumentResponse(obj);
	}

	public CommunicationResponse getCommunicationByCaseId(Integer id) {
		var obj = CaseService.extracted(caseRepository,id);
		return Helper.Convert.convertCommunicationResponse(obj);

	}

	public void createHearingByCaseId(Integer caseId, CaseHearingRequest caseHearingRequest) {
		var obj = CaseService.extracted(caseRepository,caseId);
		Proceeding hearing = Helper.Creator.createProceeding(caseHearingRequest);
		obj.getProceeding().add(hearing);
		obj.setHearingsCount(obj.getHearingsCount()+1);
		if(obj.getHearingsCount()<10) {
			hearing.setOrderType("H0" + obj.getHearingsCount());
		}
		else {
			hearing.setOrderType("H" + obj.getHearingsCount());
		}
		proceedingRepository.save(hearing);
		caseRepository.save(obj);
	}

	public List<HearingResponse> getHearingsByCaseId(Integer caseId) {
		return CaseService.extracted(caseRepository,caseId)
				.getProceeding()
				.stream()
				.map(Helper.Convert::convertHearingResponse)
				.toList();
	}

	public void updateHearingByHearingId(Integer hearingId, Date date) {
		var proceedingOptional = proceedingRepository.findById(hearingId);
		if(proceedingOptional.isEmpty()) throw new RuntimeException("hearing id does not exist");
		var proceeding = proceedingOptional.get();
		proceeding.setHearingDate(date);
		proceedingRepository.save(proceeding);
	}

	public void deleteHearingById(Integer hearingId) {
		var proceeding = proceedingRepository.findById(hearingId);
		if(proceeding.isEmpty()) throw new RuntimeException("hearing id does not exist");
		var obj = caseRepository.findByProceeding_Id(hearingId);
		if(obj.isEmpty()) throw new RuntimeException("hearing is orphaned");
		obj.get().getProceeding().remove(proceeding.get());
		caseRepository.save(obj.get());
		proceedingRepository.deleteById(hearingId);
	}
	@Autowired
	private CodeComponent codeComponent;
	@Autowired
	private MediaService mediaService;
	public ResponseEntity<?> createOrderByCaseId(AdminOrderRequest adminOrder) throws IOException {

		var obj = CaseService.extracted(caseRepository,adminOrder.getCaseId());
		obj.setOrdersCount(obj.getOrdersCount()+1);

		CaseOrder caseOrder = Helper.Creator.createCaseOrder(adminOrder);

		{	//creating for order sequence
			if (obj.getOrdersCount() < 10)
				caseOrder.setSequence("K0" + obj.getHearingsCount());
			else
				caseOrder.setSequence("K" + obj.getHearingsCount());
		}
		MetaDocInfo metaDocInfo = codeComponent.getMetaCode("order", caseOrder.getSequence(), adminOrder.getCaseId(),0, adminOrder.getFile());
		mediaService.uploadFile(metaDocInfo);
		mediaService.saveDocumentInDB(metaDocInfo);
		caseOrder.setFileName(metaDocInfo.getFileName());
		obj.getOrders().add(caseOrder);
		caseOrderRepository.save(caseOrder);
		caseRepository.save(obj);
		return ResponseEntity.ok("order created");
	}
}
