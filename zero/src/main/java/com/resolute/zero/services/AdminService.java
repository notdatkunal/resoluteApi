package com.resolute.zero.services;

import com.resolute.zero.models.Case;
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

        var caseObj = new Case();



        caseRepository.save(caseObj);
    }

    public void addBorrower(BorrowerRequest request) {


    }

    public void addArbitrator(ArbitratorRequest request) {
    }

    public void addBank(BankRequest request) {
    }
}
