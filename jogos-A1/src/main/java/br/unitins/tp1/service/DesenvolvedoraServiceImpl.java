package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.DesenvolvedoraDTO;
import br.unitins.tp1.dto.DesenvolvedoraResponseDTO;
import br.unitins.tp1.model.Desenvolvedora;
import br.unitins.tp1.repository.DesenvolvedoraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class DesenvolvedoraServiceImpl implements DesenvolvedoraService {

    @Inject
    DesenvolvedoraRepository repository;

    @Override
    @Transactional
    public DesenvolvedoraResponseDTO create(DesenvolvedoraDTO dto) {
        validarUnicidadeNome(null, dto.nome());

        Desenvolvedora dev = new Desenvolvedora();
        mapDtoToEntity(dto, dev);

        repository.persist(dev);
        return DesenvolvedoraResponseDTO.valueOf(dev);
    }

    @Override
    @Transactional
    public DesenvolvedoraResponseDTO update(Long id, DesenvolvedoraDTO dto) {
        Desenvolvedora dev = repository.findById(id);
        if (dev == null) {
            throw new NotFoundException("Desenvolvedora não encontrada com o ID: " + id);
        }

        validarUnicidadeNome(id, dto.nome());
        mapDtoToEntity(dto, dev);

        return DesenvolvedoraResponseDTO.valueOf(dev);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Desenvolvedora não encontrada para exclusão com o ID: " + id);
        }
    }

    @Override
    public DesenvolvedoraResponseDTO findById(Long id) {
        Desenvolvedora dev = repository.findById(id);
        if (dev == null) {
            throw new NotFoundException("Desenvolvedora não encontrada com o ID: " + id);
        }
        return DesenvolvedoraResponseDTO.valueOf(dev);
    }

    @Override
    public List<DesenvolvedoraResponseDTO> findAll() {
        return repository.listAll().stream()
                .map(DesenvolvedoraResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<DesenvolvedoraResponseDTO> findByNome(String nome) {
        return repository.findByNome(nome).stream()
                .map(DesenvolvedoraResponseDTO::valueOf)
                .toList();
    }

    private void mapDtoToEntity(DesenvolvedoraDTO dto, Desenvolvedora dev) {
        dev.setNome(dto.nome());
        dev.setPaisOrigem(dto.paisOrigem());
        dev.setAnoFundacao(dto.anoFundacao());
        dev.setWebsite(dto.website());
    }

    private void validarUnicidadeNome(Long id, String nome) {
        Desenvolvedora existente = repository.findByNomeExato(nome);
        if (existente != null && (id == null || !existente.getId().equals(id))) {
            throw new BadRequestException("Já existe uma desenvolvedora cadastrada com o nome: " + nome);
        }
    }
}
