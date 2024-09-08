package com.easypark.solutionsback.dto.request;

import com.easypark.solutionsback.enun.EnumStatusVacancy;

public record VacancyRequestDTO(int position, EnumStatusVacancy status) {
}
