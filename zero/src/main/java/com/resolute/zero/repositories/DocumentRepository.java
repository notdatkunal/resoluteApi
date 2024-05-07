package com.resolute.zero.repositories;

import com.resolute.zero.models.CaseDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<CaseDocument,Integer> {
}
