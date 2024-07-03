package com.resolute.zero.domains;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankCaseRepository extends JpaRepository<BankCase, Integer> {

    Optional<BankCase> findByCaseNo(String caseNo);
}