package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PlataformaDTO(
        @NotBlank(message = "O nome da plataforma deve ser informado.")
        @Size(min = 2, max = 60, message = "O nome deve ter entre 2 e 60 caracteres.")
        String nome,

        @NotBlank(message = "O fabricante da plataforma deve ser informado.")
        @Size(min = 2, max = 100, message = "O fabricante deve ter entre 2 e 100 caracteres.")
        String fabricante) {
}
