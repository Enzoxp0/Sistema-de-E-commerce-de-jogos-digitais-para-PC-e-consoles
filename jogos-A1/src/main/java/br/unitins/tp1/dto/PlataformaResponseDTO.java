package br.unitins.tp1.dto;

import br.unitins.tp1.model.Plataforma;

public record PlataformaResponseDTO(
        Long id,
        String nome,
        String fabricante) {
    public static PlataformaResponseDTO valueOf(Plataforma p) {
        if (p == null)
            return null;
        return new PlataformaResponseDTO(p.getId(), p.getNome(), p.getFabricante());
    }
}
