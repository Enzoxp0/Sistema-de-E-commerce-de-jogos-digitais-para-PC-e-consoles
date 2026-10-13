package br.unitins.tp1.service;

import java.util.List;
import br.unitins.tp1.dto.FuncionarioDTO;
import br.unitins.tp1.dto.FuncionarioResponseDTO;

public interface FuncionarioService {

    FuncionarioResponseDTO create(FuncionarioDTO dto);

    FuncionarioResponseDTO update(Long id, FuncionarioDTO dto);

    void delete(Long id);

    FuncionarioResponseDTO findById(Long id);

    List<FuncionarioResponseDTO> findAll();

    List<FuncionarioResponseDTO> findByNome(String nome);

    List<FuncionarioResponseDTO> findByCargo(String cargo);
}
