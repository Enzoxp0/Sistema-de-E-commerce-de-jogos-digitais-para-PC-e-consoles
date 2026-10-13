package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.PlataformaDTO;
import br.unitins.tp1.dto.PlataformaResponseDTO;
import br.unitins.tp1.model.Plataforma;
import br.unitins.tp1.repository.PlataformaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PlataformaServiceImpl implements PlataformaService {

    @Inject
    PlataformaRepository repository;

    @Override
    @Transactional
    public PlataformaResponseDTO create(PlataformaDTO dto) {
        validarUnicidadeNome(null, dto.nome());

        Plataforma plataforma = new Plataforma();
        mapDtoToEntity(dto, plataforma);

        repository.persist(plataforma);
        return PlataformaResponseDTO.valueOf(plataforma);
    }

    @Override
    @Transactional
    public PlataformaResponseDTO update(Long id, PlataformaDTO dto) {
        Plataforma plataforma = repository.findById(id);
        if (plataforma == null) {
            throw new NotFoundException("Plataforma não encontrada com o ID: " + id);
        }

        validarUnicidadeNome(id, dto.nome());
        mapDtoToEntity(dto, plataforma);

        return PlataformaResponseDTO.valueOf(plataforma);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Plataforma não encontrada para exclusão com o ID: " + id);
        }
    }

    @Override
    public PlataformaResponseDTO findById(Long id) {
        Plataforma plataforma = repository.findById(id);
        if (plataforma == null) {
            throw new NotFoundException("Plataforma não encontrada com o ID: " + id);
        }
        return PlataformaResponseDTO.valueOf(plataforma);
    }

    @Override
    public List<PlataformaResponseDTO> findAll() {
        return repository.listAll().stream()
                .map(PlataformaResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<PlataformaResponseDTO> findByNome(String nome) {
        return repository.findByNome(nome).stream()
                .map(PlataformaResponseDTO::valueOf)
                .toList();
    }

    private void mapDtoToEntity(PlataformaDTO dto, Plataforma plataforma) {
        plataforma.setNome(dto.nome());
        plataforma.setFabricante(dto.fabricante());
    }

    private void validarUnicidadeNome(Long id, String nome) {
        Plataforma existente = repository.findByNomeExato(nome);
        if (existente != null && (id == null || !existente.getId().equals(id))) {
            throw new BadRequestException("Já existe uma plataforma cadastrada com o nome: " + nome);
        }
    }
}
