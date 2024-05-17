package com.resolute.zero.repositories;

import com.resolute.zero.domains.CaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<CaseOrder,Integer> {
}
