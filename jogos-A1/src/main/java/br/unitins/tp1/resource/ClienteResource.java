package br.unitins.tp1.resource;

import java.util.List;
import br.unitins.tp1.dto.ClienteDTO;
import br.unitins.tp1.dto.ClienteResponseDTO;
import br.unitins.tp1.service.ClienteService;
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

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @GET
    public Response findAll() {
        return Response.ok(clienteService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(clienteService.findById(id)).build();
    }

    @POST
    public Response create(@Valid ClienteDTO dto) {
        ClienteResponseDTO response = clienteService.create(dto);
        return Response.status(Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid ClienteDTO dto) {
        ClienteResponseDTO response = clienteService.update(id, dto);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        clienteService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/search/nome/{nome}")
    public Response findByNomePath(@PathParam("nome") String nome) {
        return Response.ok(clienteService.findByNome(nome)).build();
    }

    @GET
    @Path("/search")
    public Response search(@QueryParam("nome") String nome, @QueryParam("nickname") String nickname) {
        if (nickname != null && !nickname.isBlank()) {
            return Response.ok(clienteService.findByNickname(nickname)).build();
        }
        if (nome != null && !nome.isBlank()) {
            return Response.ok(clienteService.findByNome(nome)).build();
        }
        return Response.ok(clienteService.findAll()).build();
    }

    @GET
    @Path("/search/nickname/{nickname}")
    public Response findByNickname(@PathParam("nickname") String nickname) {
        return Response.ok(clienteService.findByNickname(nickname)).build();
    }
}
