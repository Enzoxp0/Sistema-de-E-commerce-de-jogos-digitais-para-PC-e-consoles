package br.unitins.tp1.resource;

import java.math.BigDecimal;
import java.util.List;

import br.unitins.tp1.dto.JogoDTO;
import br.unitins.tp1.dto.JogoResponseDTO;
import br.unitins.tp1.service.JogoService;
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

@Path("/jogos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JogoResource {

    @Inject
    JogoService jogoService;

    @GET
    public Response findAll() {
        return Response.ok(jogoService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(jogoService.findById(id)).build();
    }

    @POST
    public Response create(@Valid JogoDTO dto) {
        JogoResponseDTO response = jogoService.create(dto);
        return Response.status(Status.CREATED).entity(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid JogoDTO dto) {
        JogoResponseDTO response = jogoService.update(id, dto);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        jogoService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/search/titulo/{titulo}")
    public Response findByTitulo(@PathParam("titulo") String titulo) {
        return Response.ok(jogoService.findByTitulo(titulo)).build();
    }

    @GET
    @Path("/search/genero/{idGenero}")
    public Response findByGenero(@PathParam("idGenero") Integer idGenero) {
        return Response.ok(jogoService.findByGenero(idGenero)).build();
    }

    @GET
    @Path("/search/plataforma/{idPlataforma}")
    public Response findByPlataforma(@PathParam("idPlataforma") Long idPlataforma) {
        return Response.ok(jogoService.findByPlataforma(idPlataforma)).build();
    }

    @GET
    @Path("/search/desenvolvedora/{idDesenvolvedora}")
    public Response findByDesenvolvedora(@PathParam("idDesenvolvedora") Long idDesenvolvedora) {
        return Response.ok(jogoService.findByDesenvolvedora(idDesenvolvedora)).build();
    }

    @GET
    @Path("/search/preco/{preco}")
    public Response findByPrecoAte(@PathParam("preco") BigDecimal preco) {
        return Response.ok(jogoService.findByPrecoAte(preco)).build();
    }
}