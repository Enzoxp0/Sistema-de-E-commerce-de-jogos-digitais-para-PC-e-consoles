package br.unitins.tp1.dto;

import br.unitins.tp1.model.RequisitoSistema;

public record RequisitoSistemaResponseDTO(
        Long id,
        String so,
        String processador,
        String memoriaRam,
        String placaDeVideo,
        String armazenamento) {

    public static RequisitoSistemaResponseDTO valueOf(RequisitoSistema r) {
        if (r == null) {
            return null;
        }
        return new RequisitoSistemaResponseDTO(
                r.getId(),
                r.getSo(),
                r.getProcessador(),
                r.getMemoriaRam(),
                r.getPlacaDeVideo(),
                r.getArmazenamento());
    }
}
