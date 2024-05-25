package com.resolute.zero.services;


import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resolute.zero.domains.BankCase;

import com.resolute.zero.helpers.Helper;
import com.resolute.zero.repositories.CaseRepository;
import com.resolute.zero.responses.CaseHistoryResponse;

@RequiredArgsConstructor
@Service
public class CaseService {
	
	
	@Autowired
	private final CaseRepository caseRepository;
	
	public CaseHistoryResponse getCaseHistoryByCaseId(Integer caseId) {
	    return Helper.Convert.convertCaseHistoryResponse(extracted(caseRepository,caseId));
	}

	
	private static BankCase extracted(CaseRepository repository,Integer caseId) {
		var caseOptional = repository.findById(caseId);
		if(caseOptional.isEmpty()) throw new RuntimeException("case Id does not exist");
		return caseOptional.get();
	}

}
