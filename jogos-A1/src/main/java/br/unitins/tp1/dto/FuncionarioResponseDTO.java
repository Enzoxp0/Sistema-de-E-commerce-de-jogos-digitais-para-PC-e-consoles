package br.unitins.tp1.dto;

import java.math.BigDecimal;
import br.unitins.tp1.model.Funcionario;

public record FuncionarioResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        String matricula,
        String cargo,
        BigDecimal salario) {

    public static FuncionarioResponseDTO valueOf(Funcionario f) {
        if (f == null) {
            return null;
        }
        return new FuncionarioResponseDTO(
                f.getId(),
                f.getNome(),
                f.getCpf(),
                f.getEmail(),
                f.getMatricula(),
                f.getCargo(),
                f.getSalario());
    }

    public static FuncionarioResponseDTO fromEntity(Funcionario f) {
        return valueOf(f);
    }
}
