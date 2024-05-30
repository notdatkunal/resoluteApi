package com.resolute.zero.services;


import com.resolute.zero.requests.CaseHearingRequest;
import com.resolute.zero.domains.Proceeding;
import com.resolute.zero.repositories.ProceedingRepository;
import com.resolute.zero.requests.HearingResponse;
import com.resolute.zero.responses.CaseDocumentsResponse;
import com.resolute.zero.responses.CommunicationResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resolute.zero.domains.BankCase;

import com.resolute.zero.helpers.Helper;
import com.resolute.zero.repositories.CaseRepository;
import com.resolute.zero.responses.CaseHistoryResponse;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CaseService {
	
	
	@Autowired
	private final CaseRepository caseRepository;
	@Autowired
	private final ProceedingRepository proceedingRepository;

	public CaseHistoryResponse getCaseHistoryByCaseId(Integer caseId) {
		var obj = CaseService.extracted(caseRepository,caseId);
	    return Helper.Convert.convertCaseHistoryResponse(obj);
	}

	
	private static BankCase extracted(CaseRepository repository,Integer caseId) {
		var caseOptional = repository.findById(caseId);
		if(caseOptional.isEmpty()) throw new RuntimeException("case Id does not exist");
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
}
