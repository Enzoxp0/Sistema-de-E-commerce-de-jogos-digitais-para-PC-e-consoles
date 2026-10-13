package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.PlataformaDTO;
import br.unitins.tp1.dto.PlataformaResponseDTO;

public interface PlataformaService {

    PlataformaResponseDTO create(PlataformaDTO dto);

    PlataformaResponseDTO update(Long id, PlataformaDTO dto);

    void delete(Long id);

    PlataformaResponseDTO findById(Long id);

    List<PlataformaResponseDTO> findAll();

    List<PlataformaResponseDTO> findByNome(String nome);
}
