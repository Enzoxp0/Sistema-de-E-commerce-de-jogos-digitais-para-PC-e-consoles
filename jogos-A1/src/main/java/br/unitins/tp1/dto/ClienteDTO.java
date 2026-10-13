package br.unitins.tp1.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteDTO(
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

        @NotBlank(message = "O nickname deve ser informado.")
        @Size(min = 3, max = 50, message = "O nickname deve ter entre 3 e 50 caracteres.")
        String nickname,

        @Size(max = 20, message = "O telefone deve ter no máximo 20 caracteres.")
        String telefone) {
}
