package com.resolute.zero.services;

import com.resolute.zero.domains.Arbitrator;
import com.resolute.zero.domains.BankCase;
import com.resolute.zero.exceptions.ErrorResponse;
import com.resolute.zero.helpers.Helper;
import com.resolute.zero.repositories.ArbitratorRepository;
import com.resolute.zero.repositories.BankRepository;
import com.resolute.zero.repositories.CaseRepository;
import com.resolute.zero.requests.AdminCaseRequest;
import com.resolute.zero.requests.ArbitratorRequest;
import com.resolute.zero.requests.BankRequest;
import com.resolute.zero.requests.BorrowerRequest;
import com.resolute.zero.responses.ArbitratorResponse;
import com.resolute.zero.responses.CaseDocumentsResponse;
import com.resolute.zero.responses.CaseResponse;
import com.resolute.zero.responses.DocumentResponse;
import lombok.Builder;
import lombok.Data;
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
}
