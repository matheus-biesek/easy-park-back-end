package com.easypark.solutionsback.repository;

import com.easypark.solutionsback.model.Adm;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AdmRepository extends JpaRepository<Adm, UUID> {
}
