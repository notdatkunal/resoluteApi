package com.resolute.zero.repositories;

import com.resolute.zero.models.Proceeding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProceedingRepository extends JpaRepository<Proceeding,Integer> {
}
