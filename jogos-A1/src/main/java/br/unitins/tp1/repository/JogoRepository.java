package br.unitins.tp1.repository;

import java.math.BigDecimal;
import java.util.List;

import br.unitins.tp1.model.Genero;
import br.unitins.tp1.model.Jogo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JogoRepository implements PanacheRepository<Jogo> {

    public List<Jogo> findByTitulo(String titulo) {
        return find("UPPER(titulo) LIKE UPPER(?1)", "%" + titulo + "%").list();
    }

    public List<Jogo> findByGenero(Genero genero) {
        return find("genero", genero).list();
    }

    public List<Jogo> findByPlataforma(Long idPlataforma) {
        return find("select distinct j from Jogo j join j.plataformas p where p.id = ?1", idPlataforma).list();
    }

    public List<Jogo> findByDesenvolvedora(Long idDesenvolvedora) {
        return find("desenvolvedora.id", idDesenvolvedora).list();
    }

    public List<Jogo> findByPrecoAte(BigDecimal preco) {
        return find("preco <= ?1", preco).list();
    }
}