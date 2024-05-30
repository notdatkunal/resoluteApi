package com.resolute.zero.repositories;

import com.resolute.zero.domains.Arbitrator;
import com.resolute.zero.domains.DocSubType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocSubTypeRepository extends JpaRepository<DocSubType,Integer> {


}
