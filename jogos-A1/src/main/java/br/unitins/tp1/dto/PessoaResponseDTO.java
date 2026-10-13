package br.unitins.tp1.dto;

import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.model.Funcionario;
import br.unitins.tp1.model.Pessoa;

public record PessoaResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        String tipo) {

    public static PessoaResponseDTO valueOf(Pessoa p) {
        if (p == null) {
            return null;
        }

        String tipo = "PESSOA";
        if (p instanceof Cliente) {
            tipo = "CLIENTE";
        } else if (p instanceof Funcionario) {
            tipo = "FUNCIONARIO";
        }

        return new PessoaResponseDTO(
                p.getId(),
                p.getNome(),
                p.getCpf(),
                p.getEmail(),
                tipo);
    }

    public static PessoaResponseDTO fromEntity(Pessoa p) {
        return valueOf(p);
    }
}
