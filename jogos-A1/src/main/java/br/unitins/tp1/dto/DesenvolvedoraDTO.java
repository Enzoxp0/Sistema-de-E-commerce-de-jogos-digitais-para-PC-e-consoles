package br.unitins.tp1.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DesenvolvedoraDTO(
        @NotBlank(message = "O nome da desenvolvedora deve ser informado.")
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
        String nome,

        @Size(max = 60, message = "O país de origem deve ter no máximo 60 caracteres.")
        String paisOrigem,

        @Min(value = 1950, message = "O ano de fundação deve ser igual ou superior a 1950.")
        @Max(value = 2100, message = "O ano de fundação deve ser válido.")
        Integer anoFundacao,

        @Size(max = 150, message = "O website deve ter no máximo 150 caracteres.")
        String website) {
}
