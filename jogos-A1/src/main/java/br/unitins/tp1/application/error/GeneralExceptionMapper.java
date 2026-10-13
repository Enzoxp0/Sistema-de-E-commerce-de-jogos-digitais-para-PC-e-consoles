package br.unitins.tp1.application.error;

import java.net.URI;

import org.jboss.logging.Logger;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Mapper global de fallback para exceções não tratadas (500 Internal Server Error).
 * Mapeia para Problem Details (RFC 9457) sem expor detalhes internos sensíveis ao cliente.
 */
@Provider
@Priority(Priorities.USER + 200)
public class GeneralExceptionMapper implements ExceptionMapper<Throwable> {

    private static final Logger LOG = Logger.getLogger(GeneralExceptionMapper.class);
    private static final URI TYPE = URI.create("urn:problem-type:internal-server-error");
    private static final String TITLE = "Erro interno no servidor";

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Throwable exception) {
        LOG.error("Erro interno não tratado na aplicação", exception);

        ProblemDetails problem = ProblemDetails.builder()
                .type(TYPE)
                .title(TITLE)
                .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .detail("Ocorreu um erro interno inesperado no servidor.")
                .instance(ProblemDetailsUtils.getRequestPath(uriInfo))
                .build();

        return problem.toResponse();
    }
}
