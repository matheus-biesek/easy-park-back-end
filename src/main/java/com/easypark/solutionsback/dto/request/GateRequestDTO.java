package com.easypark.solutionsback.dto.request;

import com.easypark.solutionsback.enun.EnumGate;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GateRequestDTO {
    @NotNull(message = "O gate do portão não pode ser nulo!")
    private EnumGate gate;
}
