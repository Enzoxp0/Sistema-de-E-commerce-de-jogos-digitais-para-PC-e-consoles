package br.unitins.tp1.resource;

import java.util.List;
import br.unitins.tp1.dto.PessoaResponseDTO;
import br.unitins.tp1.model.Pessoa;
import br.unitins.tp1.repository.PessoaRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pessoas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaResource {

    @Inject
    PessoaRepository pessoaRepository;

    @GET
    public Response findAll() {
        List<PessoaResponseDTO> list = pessoaRepository.listAll().stream()
                .map(PessoaResponseDTO::valueOf)
                .toList();
        return Response.ok(list).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Pessoa pessoa = pessoaRepository.findById(id);
        if (pessoa == null) {
            throw new NotFoundException("Pessoa não encontrada com o ID: " + id);
        }
        return Response.ok(PessoaResponseDTO.valueOf(pessoa)).build();
    }
}
