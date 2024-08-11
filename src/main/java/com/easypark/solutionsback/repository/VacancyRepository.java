package com.easypark.solutionsback.repository;

import com.easypark.solutionsback.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface VacancyRepository extends JpaRepository<Vacancy, UUID> {
    Vacancy findByPosition(int position);
}
