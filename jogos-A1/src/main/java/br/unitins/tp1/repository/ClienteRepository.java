package br.unitins.tp1.repository;

import java.util.List;
import br.unitins.tp1.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public List<Cliente> findByNome(String nome) {
        return find("upper(nome) LIKE upper(?1)", "%" + nome + "%").list();
    }

    public List<Cliente> findByNickname(String nickname) {
        return find("upper(nickname) LIKE upper(?1)", "%" + nickname + "%").list();
    }

    public Cliente findByCpf(String cpf) {
        return find("cpf", cpf).firstResult();
    }
}
