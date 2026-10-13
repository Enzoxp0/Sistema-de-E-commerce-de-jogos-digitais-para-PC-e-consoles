package br.unitins.tp1.service;

import java.util.List;
import br.unitins.tp1.dto.ClienteDTO;
import br.unitins.tp1.dto.ClienteResponseDTO;

public interface ClienteService {

    ClienteResponseDTO create(ClienteDTO dto);

    ClienteResponseDTO update(Long id, ClienteDTO dto);

    void delete(Long id);

    ClienteResponseDTO findById(Long id);

    List<ClienteResponseDTO> findAll();

    List<ClienteResponseDTO> findByNome(String nome);

    List<ClienteResponseDTO> findByNickname(String nickname);
}
