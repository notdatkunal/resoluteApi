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

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CaseService {
	
	
	@Autowired
	private final CaseRepository caseRepository;
	
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
	@Autowired
	private ProceedingRepository proceedingRepository;
	public void createHearingByCaseId(Integer caseId, CaseHearingRequest caseHearingRequest) {
		var obj = CaseService.extracted(caseRepository,caseId);
		Proceeding hearing = Helper.Creator.createProceeding(caseHearingRequest);
		obj.getProceeding().add(hearing);
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
}
