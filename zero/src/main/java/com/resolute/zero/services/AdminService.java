package com.resolute.zero.services;

import com.resolute.zero.helpers.Helper;
import com.resolute.zero.repositories.ArbitratorRepository;
import com.resolute.zero.repositories.BankRepository;
import com.resolute.zero.repositories.CaseRepository;
import com.resolute.zero.requests.AdminCaseRequest;
import com.resolute.zero.requests.ArbitratorRequest;
import com.resolute.zero.requests.BankRequest;
import com.resolute.zero.requests.BorrowerRequest;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Builder
@Service
public class AdminService {

    @Autowired
    private CaseRepository caseRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private ArbitratorRepository arbitratorRepository;
    public void saveCase(AdminCaseRequest request) {
        var caseObj =  Helper.createCase(request);
        caseRepository.save(caseObj);
    }
    public void addBorrower(BorrowerRequest request) {
        throw new RuntimeException();
    }
    public void addArbitrator(ArbitratorRequest request) {
            var arbitratorObj = Helper.createArbitrator(request);
            arbitratorRepository.save(arbitratorObj);
    }
    public void addBank(BankRequest request) {
        var bank = Helper.createBank(request);
        bankRepository.save(bank);
    }
}
