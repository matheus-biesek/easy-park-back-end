package com.easypark.solutionsback.dto.request;

import com.easypark.solutionsback.enun.EnumGate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeGateStatusRequestDTO {
    @NotNull(message = "O gate do portão não pode ser nulo!")
    private EnumGate gate;
    
    @NotNull(message = "O status do portão não pode ser nulo, vazio ou em branco!")
    private boolean status;
}
