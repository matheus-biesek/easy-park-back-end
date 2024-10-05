package com.easypark.solutionsback.repository;

import com.easypark.solutionsback.model.Vacancy;
import com.easypark.solutionsback.model.VacancyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface VacancyHistoryRepository extends JpaRepository<VacancyHistory, UUID> {
    List<VacancyHistory> findByVacancyAndDateBetween(Vacancy vacancy, LocalDateTime startDate, LocalDateTime endDate);
}
