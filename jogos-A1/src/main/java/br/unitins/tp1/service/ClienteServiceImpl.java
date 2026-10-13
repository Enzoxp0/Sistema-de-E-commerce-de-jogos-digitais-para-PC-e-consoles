package br.unitins.tp1.service;

import java.util.List;
import br.unitins.tp1.dto.ClienteDTO;
import br.unitins.tp1.dto.ClienteResponseDTO;
import br.unitins.tp1.model.Cliente;
import br.unitins.tp1.repository.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ClienteServiceImpl implements ClienteService {

    @Inject
    ClienteRepository repository;

    @Inject
    br.unitins.tp1.repository.PessoaRepository pessoaRepository;

    @Override
    @Transactional
    public ClienteResponseDTO create(ClienteDTO dto) {
        validarUnicidade(null, dto);

        Cliente cliente = new Cliente();
        mapDtoToEntity(dto, cliente);

        repository.persist(cliente);
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public ClienteResponseDTO update(Long id, ClienteDTO dto) {
        Cliente cliente = repository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado com o ID: " + id);
        }

        validarUnicidade(id, dto);
        mapDtoToEntity(dto, cliente);

        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Cliente não encontrado para exclusão com o ID: " + id);
        }
    }

    @Override
    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = repository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente não encontrado com o ID: " + id);
        }
        return ClienteResponseDTO.valueOf(cliente);
    }

    @Override
    public List<ClienteResponseDTO> findAll() {
        return repository.listAll().stream()
                .map(ClienteResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<ClienteResponseDTO> findByNome(String nome) {
        return repository.findByNome(nome).stream()
                .map(ClienteResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<ClienteResponseDTO> findByNickname(String nickname) {
        return repository.findByNickname(nickname).stream()
                .map(ClienteResponseDTO::valueOf)
                .toList();
    }

    private void mapDtoToEntity(ClienteDTO dto, Cliente cliente) {
        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());
        cliente.setEmail(dto.email());
        cliente.setNickname(dto.nickname());
        cliente.setTelefone(dto.telefone());
    }

    private void validarUnicidade(Long id, ClienteDTO dto) {
        var pessoaExistenteCpf = pessoaRepository.findByCpf(dto.cpf());
        if (pessoaExistenteCpf != null && (id == null || !pessoaExistenteCpf.getId().equals(id))) {
            throw new BadRequestException("Já existe uma pessoa cadastrada com o CPF informado (" + dto.cpf() + ").");
        }

        var pessoaExistenteEmail = pessoaRepository.findByEmail(dto.email());
        if (pessoaExistenteEmail != null && (id == null || !pessoaExistenteEmail.getId().equals(id))) {
            throw new BadRequestException("Já existe uma pessoa cadastrada com o email informado (" + dto.email() + ").");
        }

        List<Cliente> clientesComNickname = repository.findByNickname(dto.nickname());
        for (Cliente c : clientesComNickname) {
            if (id == null || !c.getId().equals(id)) {
                if (c.getNickname().equalsIgnoreCase(dto.nickname())) {
                    throw new BadRequestException("Já existe um cliente cadastrado com o nickname informado (" + dto.nickname() + ").");
                }
            }
        }
    }
}
