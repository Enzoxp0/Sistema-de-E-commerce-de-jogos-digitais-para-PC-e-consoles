package br.unitins.tp1.repository;

import java.util.List;

import br.unitins.tp1.model.Desenvolvedora;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DesenvolvedoraRepository implements PanacheRepository<Desenvolvedora> {

    public List<Desenvolvedora> findByNome(String nome) {
        if (nome == null) {
            return List.of();
        }
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }

    public Desenvolvedora findByNomeExato(String nome) {
        if (nome == null) {
            return null;
        }
        return find("UPPER(nome) = ?1", nome.toUpperCase()).firstResult();
    }
}
