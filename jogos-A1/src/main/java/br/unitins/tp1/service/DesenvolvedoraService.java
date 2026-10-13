package br.unitins.tp1.service;

import java.util.List;

import br.unitins.tp1.dto.DesenvolvedoraDTO;
import br.unitins.tp1.dto.DesenvolvedoraResponseDTO;

public interface DesenvolvedoraService {

    DesenvolvedoraResponseDTO create(DesenvolvedoraDTO dto);

    DesenvolvedoraResponseDTO update(Long id, DesenvolvedoraDTO dto);

    void delete(Long id);

    DesenvolvedoraResponseDTO findById(Long id);

    List<DesenvolvedoraResponseDTO> findAll();

    List<DesenvolvedoraResponseDTO> findByNome(String nome);
}
