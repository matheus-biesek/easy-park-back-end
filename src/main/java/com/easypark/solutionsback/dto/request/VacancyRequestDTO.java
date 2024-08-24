package com.easypark.solutionsback.dto.request;

import com.easypark.solutionsback.model.EnumStatusVacancy;

public record VacancyRequestDTO(int position, EnumStatusVacancy status) {
}
