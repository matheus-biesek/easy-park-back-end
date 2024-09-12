package com.easypark.solutionsback.dto.response;

import com.easypark.solutionsback.enun.EnumStatusVacancy;

public record StatusVacancyResponseDTO(int position, EnumStatusVacancy status) {
}
