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
import com.resolute.zero.responses.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
        var caseObj =  Helper.Creator.createCase(request);
        var arbitrator = arbitratorRepository.findById(request.getArbitratorId());
        var bank = bankRepository.findById(request.getBankId());
        if(arbitrator.isEmpty()) throw new RuntimeException("arbitrator id does not exist");
        if(bank.isEmpty()) throw new RuntimeException("bank id does not exist");
        caseObj.setArbitrator(arbitrator.get());
        caseObj.setBank(bank.get());
        caseRepository.save(caseObj);
    }
    public void addBorrower(BorrowerRequest request) {
        throw new RuntimeException();
    }
    public void addArbitrator(ArbitratorRequest request) {
            var arbitratorObj = Helper.Creator.createArbitrator(request);
            arbitratorRepository.save(arbitratorObj);
    }
    public void addBank(BankRequest request) {
        var bank = Helper.Creator.createBank(request);
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
        return Helper.Convert.convertArbitratorResponse(arbOptional.get());
    }

    public List<ArbitratorResponse> getArbitratorList() {
        var arbitratorList = arbitratorRepository.findAll();
        return arbitratorList.stream().map(Helper.Convert::convertArbitratorResponse).collect(Collectors.toList());
    }

    public AdminCaseResponse getCaseById(Integer caseId) {
        var caseOptional = caseRepository.findById(caseId);
        if(caseOptional.isEmpty()) throw new RuntimeException("Case ID Does Not Exist");
        return Helper.Convert.convertAdminCaseResponse(caseOptional.get());
    }

    public List<CaseResponse> getSearchResponse(String searchParameter, Date date) {
        if(date==null&&searchParameter==null) throw new RuntimeException("no search parameters provided");
        List<BankCase> cases = caseRepository.findAll();
        List<BankCase> caseList = new LinkedList<>();
        List<CaseResponse> caseResponseList;
        if(searchParameter!=null){
            cases.forEach(item->{
                if(item.contains(searchParameter)) caseList.add(item);
            });
        }
        if(date!=null){
            cases.forEach(item->{
                if(item.getSocFillingDate().equals(date)) caseList.add(item);
            });
        }
        caseResponseList = caseList.stream().map(Helper.Convert::convertCaseResponse).collect(Collectors.toList());
        return caseResponseList;
    }
	public List<BankResponse> getBanksList() {
		// TODO Auto-generated method stub
		var banks = bankRepository.findAll();
		LinkedList<BankResponse> bankResponses = new LinkedList<>();
		banks.forEach(item->{
			bankResponses.add(Helper.Convert.convertBankResponse(item));
			bankResponses.getLast().setSerialNo(bankResponses.size()+1);
		});
		return bankResponses;
	}
	public List<CaseResponse> getCaseList() {
		// TODO Auto-generated method stub
		var cases = caseRepository.findAll();
        return cases.stream().map(Helper.Convert::convertCaseResponse).collect(Collectors.toCollection(LinkedList::new));
	}
	
	//to be updated later
    public BankResponse getBankById(Integer bankId) {
        return bankRepository.findById(bankId)
                .map(Helper.Convert::convertBankResponse)
                .orElseThrow(()->new RuntimeException("bank Id not found")); // Or return a default BankResponse if not found
    }

    public void deleteBank(Integer bankId) {
		bankRepository.deleteById(bankId);
	}
	public void updateBank(BankRequest request, Integer bankId) {
		var bankOptional = bankRepository.findById(bankId);
		if(bankOptional.isEmpty()) throw new RuntimeException("bank Id does not exist");
		var bank = Helper.Creator.createBank(request);
		bank.setId(bankId);
        bank.setCreatedAt(bankOptional.get().getCreatedAt());
		bankRepository.save(bank);
	}
	public BorrowerResponse getBorrowerById(Integer borrowerId) {
		var borrower = borrowerRepository.findById(borrowerId);
		if(borrower.isEmpty()) throw new RuntimeException("borrower Id not found");
		
		return Helper.Convert.convertBorrowerResponse(borrower.get());
	}
	public List<BorrowerResponse> getBorrwerList() {
		var list = borrowerRepository.findAll();
		var resList = new LinkedList<BorrowerResponse>();
		list.forEach(item->resList.add(Helper.Convert.convertBorrowerResponse(item)));
		return resList;
	}

    public void updateArbitrator(ArbitratorRequest request, Integer arbitratorId) {
        var arbitratorOptional = arbitratorRepository.findById(arbitratorId);
        if(arbitratorOptional.isEmpty()) throw new RuntimeException("arbitrator Id does not exist");
        var arbitrator = Helper.Creator.createArbitrator(request);
        arbitrator.setId(arbitratorId);
        arbitrator.setRegistrationDate(arbitratorOptional.get().getRegistrationDate());
        arbitratorRepository.save(arbitrator);
    }

    public void updateCase(AdminCaseRequest request, Integer caseId) {
        var caseOptional = caseRepository.findById(caseId);
        var caseObj =  Helper.Creator.createCase(request);
        var arbitrator = arbitratorRepository.findById(request.getArbitratorId());
        var bank = bankRepository.findById(request.getBankId());
        if(caseOptional.isEmpty()) throw new RuntimeException("case id does not exist");
        if(arbitrator.isEmpty()) throw new RuntimeException("arbitrator id does not exist");
        if(bank.isEmpty()) throw new RuntimeException("bank id does not exist");
        caseObj.setArbitrator(arbitrator.get());
        caseObj.setBank(bank.get());
        caseObj.setId(caseId);
        caseObj.setCreatedAt(caseOptional.get().getCreatedAt());
        caseRepository.save(caseObj);

    }
}
