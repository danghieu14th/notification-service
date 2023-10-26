package com.example.shared.database.repository;

import com.example.shared.database.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template, Long> {
    Template findByName(String name);
}
