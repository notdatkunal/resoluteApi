package com.resolute.zero.repositories;

import com.resolute.zero.models.CaseDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaseDocumentRepository extends JpaRepository<CaseDocument,Integer> {

    Optional<CaseDocument> findCaseDocumentByCaseId(Integer caseId);
}
