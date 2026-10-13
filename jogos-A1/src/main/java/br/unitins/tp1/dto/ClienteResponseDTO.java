package br.unitins.tp1.dto;

import br.unitins.tp1.model.Cliente;

public record ClienteResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        String nickname,
        String telefone) {

    public static ClienteResponseDTO valueOf(Cliente c) {
        if (c == null) {
            return null;
        }
        return new ClienteResponseDTO(
                c.getId(),
                c.getNome(),
                c.getCpf(),
                c.getEmail(),
                c.getNickname(),
                c.getTelefone());
    }

    public static ClienteResponseDTO fromEntity(Cliente c) {
        return valueOf(c);
    }
}
