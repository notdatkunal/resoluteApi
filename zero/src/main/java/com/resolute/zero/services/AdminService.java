package com.resolute.zero.services;


import com.resolute.zero.domains.BankCase;
import com.resolute.zero.helpers.Helper;
import com.resolute.zero.repositories.ArbitratorRepository;
import com.resolute.zero.repositories.BankRepository;
import com.resolute.zero.repositories.BorrowerRepository;
import com.resolute.zero.repositories.CaseRepository;
import com.resolute.zero.requests.AdminCaseRequest;
import com.resolute.zero.requests.ArbitratorRequest;
import com.resolute.zero.requests.BankRequest;
import com.resolute.zero.requests.BorrowerRequest;
import com.resolute.zero.responses.ArbitratorResponse;
import com.resolute.zero.responses.BankResponse;
import com.resolute.zero.responses.BorrowerResponse;
import com.resolute.zero.responses.CaseResponse;
import com.resolute.zero.responses.DocumentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {

    @Autowired
    private CaseRepository caseRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private ArbitratorRepository arbitratorRepository;
    @Autowired
    private BorrowerRepository borrowerRepository;
    

    public void saveCase(AdminCaseRequest request) {
        var caseObj =  Helper.Request.createCase(request);
        caseRepository.save(caseObj);
    }
    public void addBorrower(BorrowerRequest request) {
        throw new RuntimeException();
    }
    public void addArbitrator(ArbitratorRequest request) {
            var arbitratorObj = Helper.Request.createArbitrator(request);
            arbitratorRepository.save(arbitratorObj);
    }
    public void addBank(BankRequest request) {
        var bank = Helper.Request.createBank(request);
        bankRepository.save(bank);

    }


    /**
     * this method will return list of all documents on server according to the structured model
     * */
    public List<DocumentResponse> getDocumentList() {
        throw new RuntimeException("method not implemented");
    }

    public ArbitratorResponse getArbitratorById(Integer arbitratorId) {
        var arbOptional = arbitratorRepository.findById(arbitratorId);
        if(arbOptional.isEmpty()) throw new RuntimeException("arbitrator ID does not exist");
        return Helper.Response.getArbitratorResponse(arbOptional.get());
    }

    public List<ArbitratorResponse> getArbitratorList() {
        var arbitratorList = arbitratorRepository.findAll();
        List<ArbitratorResponse> arbitratorResponseList = new LinkedList<>();
        arbitratorList.forEach(item->{
            arbitratorResponseList.add(Helper.Response.getArbitratorResponse(item));
        });
        return new ArrayList<>(arbitratorResponseList);
    }

    public CaseResponse getCaseById(Integer caseId) {
        var caseOptional = caseRepository.findById(caseId);
        if(caseOptional.isEmpty()) throw new RuntimeException("Case ID Does Not Exist");
        return Helper.Response.getCaseResponse(caseOptional.get());
    }

    public List<CaseResponse> getSearchResponse(String searchParameter, Date date) {
        if(date==null&&searchParameter==null) throw new RuntimeException("no search parameters provided");
        List<BankCase> caseList = new LinkedList<>();
        List<CaseResponse> caseResponseList = new ArrayList<>();
        if(searchParameter!=null){

        }
        if(date!=null){

        }
        caseList= new ArrayList<>(caseList);
        caseList.forEach(item->{
            caseResponseList.add(Helper.Response.getCaseResponse(item));
        });
        return caseResponseList;
    }
	public List<BankResponse> getBanksList() {
		// TODO Auto-generated method stub
		var banks = bankRepository.findAll();
		LinkedList<BankResponse> bankResponses = new LinkedList<>();
		banks.forEach(item->{
			bankResponses.add(Helper.Response.getBankResponse(item));
			bankResponses.getLast().setSerialNo(bankResponses.size()+1);
		});
		
		return bankResponses;
	}
	public List<CaseResponse> getCaseList() {
		// TODO Auto-generated method stub
		var cases = caseRepository.findAll();
		LinkedList<CaseResponse> caseResponses = new LinkedList<>();
		cases.forEach(item->{
			caseResponses.add(Helper.Response.getCaseResponse(item));
		});
		return caseResponses;
	}
	
	//to be updated later
	public BankResponse getBankById(Integer bankId) {
		// TODO Auto-generated method stub
		return Helper.Response.getBankResponse(bankRepository.findById(bankId).get());
	}
	public void deletebank(Integer bankId) {
		bankRepository.deleteById(bankId);
	}
	public void updateBank(BankRequest request, Integer bankId) {
		var bankOptional = bankRepository.findById(bankId);
		if(bankOptional.isEmpty()) throw new RuntimeException("bank Id does not exist");
		var bank = Helper.Request.createBank(request);
		bank.setId(bankId);
        bank.setCreatedAt(bankOptional.get().getCreatedAt());
		bankRepository.save(bank);
	}
	public BorrowerResponse getBorrowerById(Integer borrowerId) {
		var borrower = borrowerRepository.findById(borrowerId);
		if(borrower.isEmpty()) throw new RuntimeException("borrower Id not found");
		
		return Helper.Response.getBorrowerResponse(borrower.get());
	}
	public List<BorrowerResponse> getBorrwerList() {
		var list = borrowerRepository.findAll();
		var resList = new LinkedList<BorrowerResponse>();
		list.forEach(item->resList.add(Helper.Response.getBorrowerResponse(item)));
		return resList;
	}

    public void updateArbitrator(ArbitratorRequest request, Integer arbitratorId) {
        var arbitratorOptional = arbitratorRepository.findById(arbitratorId);
        if(arbitratorOptional.isEmpty()) throw new RuntimeException("arbitrator Id does not exist");
        var arbitrator = Helper.Request.createArbitrator(request);
        arbitrator.setId(arbitratorId);
        arbitratorRepository.save(arbitrator);
    }
}
