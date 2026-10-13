package br.unitins.tp1.resource;

import java.util.List;
import br.unitins.tp1.dto.FuncionarioDTO;
import br.unitins.tp1.dto.FuncionarioResponseDTO;
import br.unitins.tp1.service.FuncionarioService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/funcionarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FuncionarioResource {

    @Inject
    FuncionarioService funcionarioService;

    @GET
    public Response findAll() {
        return Response.ok(funcionarioService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(funcionarioService.findById(id)).build();
    }

    @POST
    public Response create(@Valid FuncionarioDTO dto) {
        FuncionarioResponseDTO response = funcionarioService.create(dto);
        return Response.status(Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid FuncionarioDTO dto) {
        FuncionarioResponseDTO response = funcionarioService.update(id, dto);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        funcionarioService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/search/nome/{nome}")
    public Response findByNomePath(@PathParam("nome") String nome) {
        return Response.ok(funcionarioService.findByNome(nome)).build();
    }

    @GET
    @Path("/search")
    public Response search(@QueryParam("nome") String nome, @QueryParam("cargo") String cargo) {
        if (cargo != null && !cargo.isBlank()) {
            return Response.ok(funcionarioService.findByCargo(cargo)).build();
        }
        if (nome != null && !nome.isBlank()) {
            return Response.ok(funcionarioService.findByNome(nome)).build();
        }
        return Response.ok(funcionarioService.findAll()).build();
    }

    @GET
    @Path("/search/cargo/{cargo}")
    public Response findByCargo(@PathParam("cargo") String cargo) {
        return Response.ok(funcionarioService.findByCargo(cargo)).build();
    }
}
