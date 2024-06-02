package com.resolute.zero.repositories;

import com.resolute.zero.domains.CaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaseOrderRepository extends JpaRepository<CaseOrder, Integer> {
}