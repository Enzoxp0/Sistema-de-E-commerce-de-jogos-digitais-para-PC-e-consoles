package br.unitins.tp1.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record FuncionarioDTO(
        @NotBlank(message = "O nome deve ser informado.")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
        String nome,

        @NotBlank(message = "O CPF deve ser informado.")
        @Pattern(regexp = "^\\d{11}$", message = "O CPF deve conter exatamente 11 dígitos numéricos.")
        String cpf,

        @NotBlank(message = "O email deve ser informado.")
        @Email(message = "Formato de email inválido.")
        @Size(max = 120, message = "O email deve ter no máximo 120 caracteres.")
        String email,

        @NotBlank(message = "A matrícula deve ser informada.")
        @Size(min = 3, max = 30, message = "A matrícula deve ter entre 3 e 30 caracteres.")
        String matricula,

        @NotBlank(message = "O cargo deve ser informado.")
        @Size(min = 2, max = 60, message = "O cargo deve ter entre 2 e 60 caracteres.")
        String cargo,

        @PositiveOrZero(message = "O salário não pode ser negativo.")
        BigDecimal salario) {
}
