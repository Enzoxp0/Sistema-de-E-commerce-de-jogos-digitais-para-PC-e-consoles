package br.unitins.tp1.repository;

import br.unitins.tp1.model.Pessoa;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PessoaRepository implements PanacheRepository<Pessoa> {

    public Pessoa findByCpf(String cpf) {
        return find("cpf", cpf).firstResult();
    }

    public Pessoa findByEmail(String email) {
        return find("email", email).firstResult();
    }
}
