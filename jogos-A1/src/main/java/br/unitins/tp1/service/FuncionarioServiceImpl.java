package br.unitins.tp1.service;

import java.util.List;
import br.unitins.tp1.dto.FuncionarioDTO;
import br.unitins.tp1.dto.FuncionarioResponseDTO;
import br.unitins.tp1.model.Funcionario;
import br.unitins.tp1.repository.FuncionarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class FuncionarioServiceImpl implements FuncionarioService {

    @Inject
    FuncionarioRepository repository;

    @Inject
    br.unitins.tp1.repository.PessoaRepository pessoaRepository;

    @Override
    @Transactional
    public FuncionarioResponseDTO create(FuncionarioDTO dto) {
        validarUnicidade(null, dto);

        Funcionario funcionario = new Funcionario();
        mapDtoToEntity(dto, funcionario);

        repository.persist(funcionario);
        return FuncionarioResponseDTO.valueOf(funcionario);
    }

    @Override
    @Transactional
    public FuncionarioResponseDTO update(Long id, FuncionarioDTO dto) {
        Funcionario funcionario = repository.findById(id);
        if (funcionario == null) {
            throw new NotFoundException("Funcionário não encontrado com o ID: " + id);
        }

        validarUnicidade(id, dto);
        mapDtoToEntity(dto, funcionario);

        return FuncionarioResponseDTO.valueOf(funcionario);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.deleteById(id)) {
            throw new NotFoundException("Funcionário não encontrado para exclusão com o ID: " + id);
        }
    }

    @Override
    public FuncionarioResponseDTO findById(Long id) {
        Funcionario funcionario = repository.findById(id);
        if (funcionario == null) {
            throw new NotFoundException("Funcionário não encontrado com o ID: " + id);
        }
        return FuncionarioResponseDTO.valueOf(funcionario);
    }

    @Override
    public List<FuncionarioResponseDTO> findAll() {
        return repository.listAll().stream()
                .map(FuncionarioResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<FuncionarioResponseDTO> findByNome(String nome) {
        return repository.findByNome(nome).stream()
                .map(FuncionarioResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<FuncionarioResponseDTO> findByCargo(String cargo) {
        return repository.findByCargo(cargo).stream()
                .map(FuncionarioResponseDTO::valueOf)
                .toList();
    }

    private void mapDtoToEntity(FuncionarioDTO dto, Funcionario funcionario) {
        funcionario.setNome(dto.nome());
        funcionario.setCpf(dto.cpf());
        funcionario.setEmail(dto.email());
        funcionario.setMatricula(dto.matricula());
        funcionario.setCargo(dto.cargo());
        funcionario.setSalario(dto.salario());
    }

    private void validarUnicidade(Long id, FuncionarioDTO dto) {
        var pessoaExistenteCpf = pessoaRepository.findByCpf(dto.cpf());
        if (pessoaExistenteCpf != null && (id == null || !pessoaExistenteCpf.getId().equals(id))) {
            throw new BadRequestException("Já existe uma pessoa cadastrada com o CPF informado (" + dto.cpf() + ").");
        }

        var pessoaExistenteEmail = pessoaRepository.findByEmail(dto.email());
        if (pessoaExistenteEmail != null && (id == null || !pessoaExistenteEmail.getId().equals(id))) {
            throw new BadRequestException("Já existe uma pessoa cadastrada com o email informado (" + dto.email() + ").");
        }

        Funcionario existenteMatricula = repository.findByMatricula(dto.matricula());
        if (existenteMatricula != null && (id == null || !existenteMatricula.getId().equals(id))) {
            throw new BadRequestException("Já existe um funcionário cadastrado com a matrícula informada (" + dto.matricula() + ").");
        }
    }
}
