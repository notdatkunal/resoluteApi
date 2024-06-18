package com.resolute.zero.domains;

import com.resolute.zero.helpers.TemplateType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemplateRepository extends JpaRepository<Template, Integer> {
    Optional<Template> findByType(TemplateType type);
}