package com.example.shared.database.repository;

import com.example.shared.database.entity.ProviderIntegration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProviderIntegrationRepository extends JpaRepository<ProviderIntegration, Long> {

}
