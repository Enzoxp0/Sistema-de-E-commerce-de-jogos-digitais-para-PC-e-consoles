package br.unitins.tp1.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.unitins.tp1.dto.JogoDTO;
import br.unitins.tp1.dto.JogoResponseDTO;
import br.unitins.tp1.model.Desenvolvedora;
import br.unitins.tp1.model.Genero;
import br.unitins.tp1.model.Jogo;
import br.unitins.tp1.model.Plataforma;
import br.unitins.tp1.model.RequisitoSistema;
import br.unitins.tp1.repository.DesenvolvedoraRepository;
import br.unitins.tp1.repository.JogoRepository;
import br.unitins.tp1.repository.PlataformaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class JogoServiceImpl implements JogoService {

    @Inject
    JogoRepository jogoRepository;

    @Inject
    PlataformaRepository plataformaRepository;

    @Inject
    DesenvolvedoraRepository desenvolvedoraRepository;

    @Override
    @Transactional
    public JogoResponseDTO create(JogoDTO dto) {
        Jogo jogo = new Jogo();
        mapDtoToEntity(dto, jogo);
        jogoRepository.persist(jogo);
        return JogoResponseDTO.valueOf(jogo);
    }

    @Override
    @Transactional
    public JogoResponseDTO update(Long id, JogoDTO dto) {
        Jogo jogo = jogoRepository.findById(id);
        if (jogo == null) {
            throw new NotFoundException("Jogo não encontrado com o ID: " + id);
        }
        mapDtoToEntity(dto, jogo);
        return JogoResponseDTO.valueOf(jogo);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!jogoRepository.deleteById(id)) {
            throw new NotFoundException("Jogo não encontrado para exclusão com o ID: " + id);
        }
    }

    @Override
    public JogoResponseDTO findById(Long id) {
        Jogo jogo = jogoRepository.findById(id);
        if (jogo == null) {
            throw new NotFoundException("Jogo não encontrado com o ID: " + id);
        }
        return JogoResponseDTO.valueOf(jogo);
    }

    @Override
    public List<JogoResponseDTO> findAll() {
        return jogoRepository.listAll().stream()
                .map(JogoResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<JogoResponseDTO> findByTitulo(String titulo) {
        return jogoRepository.findByTitulo(titulo).stream()
                .map(JogoResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<JogoResponseDTO> findByGenero(Integer idGenero) {
        Genero genero;
        try {
            genero = Genero.valueOf(idGenero);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Gênero inválido com o ID: " + idGenero);
        }
        return jogoRepository.findByGenero(genero).stream()
                .map(JogoResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<JogoResponseDTO> findByPlataforma(Long idPlataforma) {
        return jogoRepository.findByPlataforma(idPlataforma).stream()
                .map(JogoResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<JogoResponseDTO> findByDesenvolvedora(Long idDesenvolvedora) {
        return jogoRepository.findByDesenvolvedora(idDesenvolvedora).stream()
                .map(JogoResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<JogoResponseDTO> findByPrecoAte(BigDecimal preco) {
        return jogoRepository.findByPrecoAte(preco).stream()
                .map(JogoResponseDTO::valueOf)
                .toList();
    }

    private void mapDtoToEntity(JogoDTO dto, Jogo jogo) {
        jogo.setTitulo(dto.titulo());
        jogo.setDescricao(dto.descricao());
        jogo.setPreco(dto.preco());

        try {
            jogo.setGenero(Genero.valueOf(dto.idGenero()));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Gênero inválido com o ID: " + dto.idGenero());
        }

        Desenvolvedora dev = desenvolvedoraRepository.findById(dto.idDesenvolvedora());
        if (dev == null) {
            throw new NotFoundException("Desenvolvedora não encontrada com o ID: " + dto.idDesenvolvedora());
        }
        jogo.setDesenvolvedora(dev);

        List<Plataforma> plataformas = new ArrayList<>();
        if (dto.idPlataformas() != null) {
            for (Long idPlat : dto.idPlataformas()) {
                Plataforma plat = plataformaRepository.findById(idPlat);
                if (plat == null) {
                    throw new NotFoundException("Plataforma não encontrada com o ID: " + idPlat);
                }
                plataformas.add(plat);
            }
        }
        jogo.setPlataformas(plataformas);

        if (dto.requisitoSistema() != null) {
            RequisitoSistema req = jogo.getRequisitoSistema();
            if (req == null) {
                req = new RequisitoSistema();
            }
            req.setSo(dto.requisitoSistema().so());
            req.setProcessador(dto.requisitoSistema().processador());
            req.setMemoriaRam(dto.requisitoSistema().memoriaRam());
            req.setPlacaDeVideo(dto.requisitoSistema().placaDeVideo());
            req.setArmazenamento(dto.requisitoSistema().armazenamento());
            jogo.setRequisitoSistema(req);
        } else {
            jogo.setRequisitoSistema(null);
        }
    }
}
