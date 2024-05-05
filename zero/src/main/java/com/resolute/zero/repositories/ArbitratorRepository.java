package com.resolute.zero.repositories;

import com.resolute.zero.models.Arbitrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArbitratorRepository extends JpaRepository<Arbitrator,Integer> {
}