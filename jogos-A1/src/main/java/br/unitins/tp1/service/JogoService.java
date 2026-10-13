package br.unitins.tp1.service;

import java.math.BigDecimal;
import java.util.List;

import br.unitins.tp1.dto.JogoDTO;
import br.unitins.tp1.dto.JogoResponseDTO;

public interface JogoService {

    JogoResponseDTO create(JogoDTO dto);

    JogoResponseDTO update(Long id, JogoDTO dto);

    void delete(Long id);

    JogoResponseDTO findById(Long id);

    List<JogoResponseDTO> findAll();

    List<JogoResponseDTO> findByTitulo(String titulo);

    List<JogoResponseDTO> findByGenero(Integer idGenero);

    List<JogoResponseDTO> findByPlataforma(Long idPlataforma);

    List<JogoResponseDTO> findByDesenvolvedora(Long idDesenvolvedora);

    List<JogoResponseDTO> findByPrecoAte(BigDecimal preco);
}