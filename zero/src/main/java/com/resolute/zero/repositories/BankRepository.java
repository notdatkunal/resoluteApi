package com.resolute.zero.repositories;

import com.resolute.zero.domains.Bank;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank,Integer> {
	
	Optional<Bank> findBankByUsername(String username);
}
