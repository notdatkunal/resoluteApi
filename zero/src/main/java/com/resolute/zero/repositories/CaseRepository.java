package com.resolute.zero.repositories;

import com.resolute.zero.domains.BankCase;
import com.resolute.zero.domains.Proceeding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaseRepository extends JpaRepository<BankCase,Integer> {


    Optional<BankCase> findByProceeding_Id(Integer id);


    Optional<BankCase> findByProceeding(Proceeding proceeding);

    Optional<BankCase> findByOrders_Id(Integer id);

    List<BankCase> findByArbitrator_Id(Integer id);

    List<BankCase> findByBank_Id(Integer id);

    List<BankCase> findByBank_IdAndCaseType(Integer id, String caseType);

    @Query("select count(b) from BankCase b where b.caseType = ?1")
    long countByCaseType(String caseType);

    @Query("select b from BankCase b where b.bank.id = ?1 and b.caseType = ?2 and b.caseStatus = ?3")
    List<BankCase> findByBank_IdAndCaseTypeAndCaseStatus(Integer id, String caseType, String caseStatus);

    long countByBank_IdAndCaseTypeAndCaseStatus(Integer id, String caseType, String caseStatus);

    long countByBank_IdAndCaseType(Integer id, String caseType);
}
