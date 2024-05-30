package com.resolute.zero.repositories;

import com.resolute.zero.domains.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document,Integer> {

    List<Document> findByCaseId(Integer caseId);
}
