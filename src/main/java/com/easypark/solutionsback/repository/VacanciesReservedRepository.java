package com.easypark.solutionsback.repository;

import com.easypark.solutionsback.model.EnumStatusVacancy;
import com.easypark.solutionsback.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;


public interface VacanciesReservedRepository extends JpaRepository<Vacancy, UUID> {

    List<Vacancy> findByAll();
}
