package com.resolute.zero.repositories;

import com.resolute.zero.domains.BankCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaseRepository extends JpaRepository<BankCase,Integer> {
}
