package br.unitins.tp1.application.error;

import java.net.URI;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Mapper para exceções gerais da camada Web/JAX-RS (ex: 405 Method Not Allowed, 415 Unsupported Media Type).
 * Mapeia para Problem Details (RFC 9457).
 */
@Provider
@Priority(Priorities.USER + 100)
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(WebApplicationException exception) {
        int status = exception.getResponse().getStatus();
        Response.Status statusEnum = Response.Status.fromStatusCode(status);
        String title = statusEnum != null ? statusEnum.getReasonPhrase() : "Erro HTTP";

        String detail = exception.getMessage();
        if (detail == null || detail.isBlank()) {
            detail = "Ocorreu um erro ao processar a requisição HTTP.";
        }

        ProblemDetails problem = ProblemDetails.builder()
                .type(URI.create("urn:problem-type:http-" + status))
                .title(title)
                .status(status)
                .detail(detail)
                .instance(ProblemDetailsUtils.getRequestPath(uriInfo))
                .build();

        return problem.toResponse();
    }
}
