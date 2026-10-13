package br.unitins.tp1.resource;

import java.util.List;

import br.unitins.tp1.dto.DesenvolvedoraDTO;
import br.unitins.tp1.dto.DesenvolvedoraResponseDTO;
import br.unitins.tp1.service.DesenvolvedoraService;
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/desenvolvedoras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DesenvolvedoraResource {

    @Inject
    DesenvolvedoraService desenvolvedoraService;

    @GET
    public Response findAll() {
        return Response.ok(desenvolvedoraService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(desenvolvedoraService.findById(id)).build();
    }

    @POST
    public Response create(@Valid DesenvolvedoraDTO dto) {
        DesenvolvedoraResponseDTO response = desenvolvedoraService.create(dto);
        return Response.status(Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid DesenvolvedoraDTO dto) {
        DesenvolvedoraResponseDTO response = desenvolvedoraService.update(id, dto);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        desenvolvedoraService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/search/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        return Response.ok(desenvolvedoraService.findByNome(nome)).build();
    }
}
