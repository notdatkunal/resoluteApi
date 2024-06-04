package com.resolute.zero.repositories;

import com.resolute.zero.domains.BankCase;
import com.resolute.zero.domains.Proceeding;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
