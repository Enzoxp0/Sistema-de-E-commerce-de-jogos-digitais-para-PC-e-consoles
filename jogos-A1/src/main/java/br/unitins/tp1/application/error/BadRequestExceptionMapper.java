package br.unitins.tp1.application.error;

import java.net.URI;

import jakarta.annotation.Priority;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Mapper centralizado para requisições inválidas (BadRequestException).
 * Mapeia para Problem Details (RFC 9457) com status 400.
 */
@Provider
@Priority(Priorities.USER)
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

    private static final URI TYPE = URI.create("urn:problem-type:bad-request");
    private static final String TITLE = "Requisição inválida";

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(BadRequestException exception) {
        String detail = exception.getMessage();
        if (detail == null || detail.isBlank()) {
            detail = "A requisição enviada é inválida ou não pôde ser processada.";
        }

        ProblemDetails problem = ProblemDetails.builder()
                .type(TYPE)
                .title(TITLE)
                .status(Response.Status.BAD_REQUEST.getStatusCode())
                .detail(detail)
                .instance(ProblemDetailsUtils.getRequestPath(uriInfo))
                .build();

        return problem.toResponse();
    }
}
