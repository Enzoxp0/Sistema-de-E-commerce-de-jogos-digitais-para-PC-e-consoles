package br.unitins.tp1.dto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import br.unitins.tp1.model.Jogo;

public record JogoResponseDTO(
        Long id,
        String titulo,
        String descricao,
        BigDecimal preco,
        String genero,
        DesenvolvedoraResponseDTO desenvolvedora,
        List<PlataformaResponseDTO> plataformas,
        RequisitoSistemaResponseDTO requisitoSistema) {

    public static JogoResponseDTO valueOf(Jogo j) {
        if (j == null) {
            return null;
        }

        List<PlataformaResponseDTO> plataformasDTO = j.getPlataformas() != null
                ? j.getPlataformas().stream().map(PlataformaResponseDTO::valueOf).toList()
                : Collections.emptyList();

        return new JogoResponseDTO(
                j.getId(),
                j.getTitulo(),
                j.getDescricao(),
                j.getPreco(),
                j.getGenero() != null ? j.getGenero().getLabel() : null,
                DesenvolvedoraResponseDTO.valueOf(j.getDesenvolvedora()),
                plataformasDTO,
                RequisitoSistemaResponseDTO.valueOf(j.getRequisitoSistema()));
    }
}