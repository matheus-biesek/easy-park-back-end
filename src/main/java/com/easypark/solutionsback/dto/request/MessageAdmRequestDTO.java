package com.easypark.solutionsback.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageAdmRequestDTO {

    @NotBlank(message = "A mensagem não pode ser nula, vazia ou em branco!")
    @Size(min = 2, max = 50, message = "O mensagem deve ter entre 2 e 50 caracteres!")
    private String message;
}
