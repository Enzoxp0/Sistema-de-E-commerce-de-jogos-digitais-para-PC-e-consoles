package br.unitins.tp1.application.error;

import java.net.URI;
import java.util.Comparator;
import java.util.List;

import jakarta.annotation.Priority;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;
import jakarta.validation.Path;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Mapper centralizado para erros de validação (@Valid, Bean Validation).
 * Mapeia ConstraintViolationException para Problem Details (RFC 9457) com status 400.
 */
@Provider
@Priority(Priorities.USER)
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final URI TYPE = URI.create("urn:problem-type:validation-error");
    private static final String TITLE = "Erro de validação";

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<Violation> violations = exception.getConstraintViolations().stream()
                .map(this::toViolation)
                .sorted(Comparator.comparing(Violation::field))
                .toList();

        ProblemDetails problem = ProblemDetails.builder()
                .type(TYPE)
                .title(TITLE)
                .status(Response.Status.BAD_REQUEST.getStatusCode())
                .detail("Um ou mais campos contêm valores inválidos.")
                .instance(ProblemDetailsUtils.getRequestPath(uriInfo))
                .violations(violations)
                .build();

        return problem.toResponse();
    }

    private Violation toViolation(ConstraintViolation<?> cv) {
        return new Violation(extractFieldName(cv), cv.getMessage());
    }

    private String extractFieldName(ConstraintViolation<?> violation) {
        StringBuilder propertyPath = new StringBuilder();

        for (Path.Node node : violation.getPropertyPath()) {
            if (node.getKind() == ElementKind.PROPERTY) {
                if (!propertyPath.isEmpty()) {
                    propertyPath.append(".");
                }
                propertyPath.append(node.getName());
            }
        }

        if (!propertyPath.isEmpty()) {
            return propertyPath.toString();
        }

        String lastNodeName = null;
        for (Path.Node node : violation.getPropertyPath()) {
            lastNodeName = node.getName();
        }

        return lastNodeName != null ? lastNodeName : violation.getPropertyPath().toString();
    }
}
