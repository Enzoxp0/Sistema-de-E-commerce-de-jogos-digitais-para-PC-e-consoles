package br.unitins.tp1.dto;

import br.unitins.tp1.model.Desenvolvedora;

public record DesenvolvedoraResponseDTO(
        Long id,
        String nome,
        String paisOrigem,
        Integer anoFundacao,
        String website) {

    public static DesenvolvedoraResponseDTO valueOf(Desenvolvedora d) {
        if (d == null) {
            return null;
        }
        return new DesenvolvedoraResponseDTO(
                d.getId(),
                d.getNome(),
                d.getPaisOrigem(),
                d.getAnoFundacao(),
                d.getWebsite());
    }
}
