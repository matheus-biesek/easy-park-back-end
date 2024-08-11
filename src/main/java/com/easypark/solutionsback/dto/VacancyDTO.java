package com.easypark.solutionsback.dto;

import com.easypark.solutionsback.model.EnumStatusVacancy;

public record VacancyDTO(int position, EnumStatusVacancy status) {
}
