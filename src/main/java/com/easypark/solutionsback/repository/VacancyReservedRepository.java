package com.easypark.solutionsback.repository;

import com.easypark.solutionsback.model.Vacancy;
import com.easypark.solutionsback.model.VacancyReserved;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface VacancyReservedRepository extends JpaRepository<VacancyReserved, UUID> {
    // Verifica se já existe uma reserva entre dois horários
    boolean existsByVacancyAndDateTimeBetween(Vacancy vacancy, LocalDateTime start, LocalDateTime end);

    // Busca as reservas de uma vaga e as ordena pela data de criação mais recente
    List<VacancyReserved> findByVacancyOrderByDateTimeDesc(Vacancy vacancy);
}