package com.resolute.zero.repositories;

import com.resolute.zero.models.Hearing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HearingRepository extends JpaRepository<Hearing,Integer> {
}
