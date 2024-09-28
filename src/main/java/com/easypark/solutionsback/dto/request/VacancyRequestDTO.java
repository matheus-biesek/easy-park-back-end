package com.easypark.solutionsback.dto.request;

import com.easypark.solutionsback.enun.EnumStatusVacancy;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VacancyRequestDTO {

    @Min(value = 1, message = "A posição tem que ser maior que 0!")
    private int position;

    @NotNull(message = "O status da vaga não pode ser nulo!")
    private EnumStatusVacancy status;
}
