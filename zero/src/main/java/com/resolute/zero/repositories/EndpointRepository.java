package com.resolute.zero.repositories;

import com.resolute.zero.domains.Endpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EndpointRepository extends JpaRepository<Endpoint, Integer>, JpaSpecificationExecutor<Endpoint> {
}