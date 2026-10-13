package br.unitins.tp1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequisitoSistemaDTO(
        @NotBlank(message = "O sistema operacional deve ser informado.")
        @Size(max = 100, message = "O SO deve ter no máximo 100 caracteres.")
        String so,

        @NotBlank(message = "O processador deve ser informado.")
        @Size(max = 150, message = "O processador deve ter no máximo 150 caracteres.")
        String processador,

        @NotBlank(message = "A memória RAM deve ser informada.")
        @Size(max = 50, message = "A memória RAM deve ter no máximo 50 caracteres.")
        String memoriaRam,

        @NotBlank(message = "A placa de vídeo deve ser informada.")
        @Size(max = 150, message = "A placa de vídeo deve ter no máximo 150 caracteres.")
        String placaDeVideo,

        @NotBlank(message = "O armazenamento deve ser informado.")
        @Size(max = 80, message = "O armazenamento deve ter no máximo 80 caracteres.")
        String armazenamento) {
}
