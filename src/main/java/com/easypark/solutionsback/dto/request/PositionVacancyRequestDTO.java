package com.easypark.solutionsback.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PositionVacancyRequestDTO {

    @Min(value = 1, message = "A posição tem que ser maior que 0!")
    private int position;
}
