package com.resolute.zero.services;


import com.resolute.zero.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteService {


    private final CaseRepository caseRepository;
    private final MediaService mediaService;
    private final ArbitratorRepository arbitratorRepository;
    private final BankRepository bankRepository;
    private final ProceedingRepository proceedingRepository;
    private final CaseOrderRepository caseOrderRepository;
    private final UserRepository userRepository;

    public void deleteCase(Integer caseId) {
        var caseOpt = caseRepository.findById(caseId);
        assert caseOpt.isEmpty():"case does not exist";

        caseOpt.get().getDocumentList().forEach(doc->
            mediaService.deleteDocument(doc.getId())
        );
        if(caseOpt.get().getArbitrator()!=null)  arbitratorRepository
                .deleteById(caseOpt.get()
                        .getArbitrator()
                        .getId());
        var bank = bankRepository.getReferenceById(caseOpt.get().getBank().getId());
        assert bank != null : "bank is null";
        bank.getBankCaseList().remove(caseOpt.get());
        bankRepository.save(bank);
        proceedingRepository.deleteAll(caseOpt.get().getProceeding());
        caseOrderRepository.deleteAll(caseOpt.get().getOrders());
    }

    public void deleteBank(Integer bankId) {
        var bankOpt = bankRepository.findById(bankId);
        assert bankOpt.isEmpty() : "bank id does not exist ";
        bankOpt.get().getBankCaseList().forEach(caseObj->deleteCase(caseObj.getId()));
        bankRepository.deleteById(bankId);
        userRepository.deleteById(bankId);
    }
}
