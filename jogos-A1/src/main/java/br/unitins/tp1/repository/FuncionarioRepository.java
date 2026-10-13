package br.unitins.tp1.repository;

import java.util.List;
import br.unitins.tp1.model.Funcionario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FuncionarioRepository implements PanacheRepository<Funcionario> {

    public List<Funcionario> findByNome(String nome) {
        return find("upper(nome) LIKE upper(?1)", "%" + nome + "%").list();
    }

    public List<Funcionario> findByCargo(String cargo) {
        return find("upper(cargo) LIKE upper(?1)", "%" + cargo + "%").list();
    }

    public Funcionario findByMatricula(String matricula) {
        return find("matricula", matricula).firstResult();
    }

    public Funcionario findByCpf(String cpf) {
        return find("cpf", cpf).firstResult();
    }
}
