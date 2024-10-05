package com.easypark.solutionsback.service;

import com.easypark.solutionsback.enun.EnumStatusVacancy;
import com.easypark.solutionsback.model.Vacancy;
import com.easypark.solutionsback.model.VacancyHistory;
import com.easypark.solutionsback.repository.VacancyHistoryRepository;
import com.easypark.solutionsback.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VacancyStatisticService {

    private final VacancyRepository vacancyRepository;
    private final VacancyHistoryRepository vacancyHistoryRepository;

    private int analyzeHistoryOccupied(List<VacancyHistory> history) {
        int occupiedCount = 0;
        for (VacancyHistory record : history) {
            if (record.getStatus() == EnumStatusVacancy.busy) {
                occupiedCount++;
            }
        }
        return occupiedCount;
    }

    public List<Integer> analyzeVacancyOccupiedAfterDay() {
        LocalDateTime startDate = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime endDate = LocalDate.now().minusDays(1).atTime(LocalTime.MAX);
        List<Vacancy> vacancies = vacancyRepository.findAll();
        return vacancies.stream().map(vacancy -> {
            List<VacancyHistory> history = vacancyHistoryRepository.findByVacancyAndDateBetween(vacancy, startDate, endDate);
            return analyzeHistoryOccupied(history);
        }).collect(Collectors.toList());
    }
}
