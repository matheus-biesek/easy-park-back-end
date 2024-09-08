package com.easypark.solutionsback.repository;

import com.easypark.solutionsback.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AdmRepository extends JpaRepository<Admin, UUID> {
}
