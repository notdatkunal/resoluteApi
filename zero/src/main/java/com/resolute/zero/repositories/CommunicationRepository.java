package com.resolute.zero.repositories;

import com.resolute.zero.domains.Communication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunicationRepository extends JpaRepository<Communication, Integer> {
}