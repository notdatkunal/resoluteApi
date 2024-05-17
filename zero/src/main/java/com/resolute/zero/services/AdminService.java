package com.resolute.zero.services;

import com.resolute.zero.models.Arbitrator;
import com.resolute.zero.models.Bank;
import com.resolute.zero.models.Case;
import com.resolute.zero.repositories.ArbitratorRepository;
import com.resolute.zero.repositories.BankRepository;
import com.resolute.zero.repositories.CaseRepository;
import com.resolute.zero.requests.AdminCaseRequest;
import com.resolute.zero.requests.ArbitratorRequest;
import com.resolute.zero.requests.BankRequest;
import com.resolute.zero.requests.BorrowerRequest;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Builder
@Service
public class AdminService {

    private CaseRepository caseRepository;
    public void saveCase(AdminCaseRequest request) {

        var caseObj =  Case.createCase(request);
        caseRepository.save(caseObj);
    }

    public void addBorrower(BorrowerRequest request) {
        throw new RuntimeException();

    }
    private ArbitratorRepository arbitratorRepository;
    public void addArbitrator(ArbitratorRequest request) {


        var arbitratorObj = new Arbitrator();

            arbitratorObj.setUserName(request.getUserName());
            arbitratorObj.setArbitratorName(request.getArbitratorName());
            arbitratorObj.setRegistrationDate(request.getRegistrationDate());
            arbitratorObj.setLocation(request.getLocation());
            arbitratorRepository.save(arbitratorObj);


    }
    private BankRepository bankRepository;
    public void addBank(BankRequest request) {

        Bank bank = new Bank();
        bank.setBankName(request.getBankName());
        bank.setLocation(request.getLocation());
        bank.setOfficerName(request.getOfficerName());
        bank.setRegistrationDate(request.getRegistrationDate());
        bank.setUsername(request.getUsername());
        bankRepository.save(bank);
    }
}
