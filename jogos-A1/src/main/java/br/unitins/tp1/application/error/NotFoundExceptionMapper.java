package br.unitins.tp1.application.error;

import java.net.URI;

import jakarta.annotation.Priority;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Mapper centralizado para recursos não encontrados (NotFoundException).
 * Mapeia para Problem Details (RFC 9457) com status 404.
 */
@Provider
@Priority(Priorities.USER)
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    private static final URI TYPE = URI.create("urn:problem-type:not-found");
    private static final String TITLE = "Recurso não encontrado";

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(NotFoundException exception) {
        String detail = exception.getMessage();
        if (detail == null || detail.isBlank()) {
            detail = "O recurso solicitado não foi encontrado.";
        }

        ProblemDetails problem = ProblemDetails.builder()
                .type(TYPE)
                .title(TITLE)
                .status(Response.Status.NOT_FOUND.getStatusCode())
                .detail(detail)
                .instance(ProblemDetailsUtils.getRequestPath(uriInfo))
                .build();

        return problem.toResponse();
    }
}
