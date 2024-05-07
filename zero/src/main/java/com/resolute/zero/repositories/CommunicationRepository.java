package com.resolute.zero.repositories;

import com.resolute.zero.models.CaseCommunication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunicationRepository extends JpaRepository<CaseCommunication,Integer> {

}
