package br.unitins.tp1.dto;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record JogoDTO(
        @NotBlank(message = "O título não pode estar em branco")
        @Size(max = 100, message = "O título deve ter no máximo 100 caracteres")
        String titulo,

        @Size(max = 1000, message = "A descrição deve ter no máximo 1000 caracteres")
        String descricao,

        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser maior que zero")
        BigDecimal preco,

        @NotNull(message = "O ID do gênero é obrigatório")
        Integer idGenero,

        @NotNull(message = "O ID da desenvolvedora é obrigatório")
        Long idDesenvolvedora,

        @NotEmpty(message = "Pelo menos uma plataforma deve ser informada")
        List<Long> idPlataformas,

        @Valid
        RequisitoSistemaDTO requisitoSistema) {
}