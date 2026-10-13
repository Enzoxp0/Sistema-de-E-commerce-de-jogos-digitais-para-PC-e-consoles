package br.unitins.tp1.application.error;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;
import jakarta.validation.Path;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

class ProblemDetailsExceptionMapperTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testProblemDetailsSerialization() throws JsonProcessingException {
        ProblemDetails problem = ProblemDetails.builder()
                .type("urn:problem-type:validation-error")
                .title("Erro de validação")
                .status(400)
                .detail("Um ou mais campos contêm valores inválidos.")
                .instance("/clientes")
                .addViolation(new Violation("cpf", "O CPF deve conter exatamente 11 dígitos numéricos."))
                .build();

        String json = objectMapper.writeValueAsString(problem);

        assertTrue(json.contains("\"type\":\"urn:problem-type:validation-error\""));
        assertTrue(json.contains("\"title\":\"Erro de validação\""));
        assertTrue(json.contains("\"status\":400"));
        assertTrue(json.contains("\"detail\":\"Um ou mais campos contêm valores inválidos.\""));
        assertTrue(json.contains("\"instance\":\"/clientes\""));
        assertTrue(json.contains("\"violations\":[{\"field\":\"cpf\",\"message\":\"O CPF deve conter exatamente 11 dígitos numéricos.\"}]"));
    }

    @Test
    void testProblemDetailsOmitViolationsWhenEmpty() throws JsonProcessingException {
        ProblemDetails problem = ProblemDetails.builder()
                .type("urn:problem-type:not-found")
                .title("Recurso não encontrado")
                .status(404)
                .detail("Cliente não encontrado com o ID: 1")
                .instance("/clientes/1")
                .build();

        String json = objectMapper.writeValueAsString(problem);

        assertFalse(json.contains("violations"));
    }

    @Test
    void testBadRequestExceptionMapper() {
        BadRequestExceptionMapper mapper = new BadRequestExceptionMapper();
        mapper.uriInfo = createMockUriInfo("/clientes");

        BadRequestException ex = new BadRequestException("CPF duplicado");
        Response response = mapper.toResponse(ex);

        assertEquals(400, response.getStatus());
        assertEquals(ProblemDetails.MEDIA_TYPE, response.getMediaType().toString());

        ProblemDetails entity = (ProblemDetails) response.getEntity();
        assertEquals(URI.create("urn:problem-type:bad-request"), entity.getType());
        assertEquals("Requisição inválida", entity.getTitle());
        assertEquals("CPF duplicado", entity.getDetail());
        assertEquals(URI.create("/clientes"), entity.getInstance());
        assertNull(entity.getViolations());
    }

    @Test
    void testIllegalArgumentExceptionMapper() {
        IllegalArgumentExceptionMapper mapper = new IllegalArgumentExceptionMapper();
        mapper.uriInfo = createMockUriInfo("/jogos");

        IllegalArgumentException ex = new IllegalArgumentException("Gênero inválido com o ID: 99");
        Response response = mapper.toResponse(ex);

        assertEquals(400, response.getStatus());
        assertEquals(ProblemDetails.MEDIA_TYPE, response.getMediaType().toString());

        ProblemDetails entity = (ProblemDetails) response.getEntity();
        assertEquals(URI.create("urn:problem-type:bad-request"), entity.getType());
        assertEquals("Requisição inválida", entity.getTitle());
        assertEquals("Gênero inválido com o ID: 99", entity.getDetail());
        assertEquals(URI.create("/jogos"), entity.getInstance());
        assertNull(entity.getViolations());
    }

    @Test
    void testNotFoundExceptionMapper() {
        NotFoundExceptionMapper mapper = new NotFoundExceptionMapper();
        mapper.uriInfo = createMockUriInfo("/clientes/99");

        NotFoundException ex = new NotFoundException("Cliente não encontrado com o ID: 99");
        Response response = mapper.toResponse(ex);

        assertEquals(404, response.getStatus());
        assertEquals(ProblemDetails.MEDIA_TYPE, response.getMediaType().toString());

        ProblemDetails entity = (ProblemDetails) response.getEntity();
        assertEquals(URI.create("urn:problem-type:not-found"), entity.getType());
        assertEquals("Recurso não encontrado", entity.getTitle());
        assertEquals("Cliente não encontrado com o ID: 99", entity.getDetail());
        assertEquals(URI.create("/clientes/99"), entity.getInstance());
        assertNull(entity.getViolations());
    }

    @Test
    void testWebApplicationExceptionMapper() {
        WebApplicationExceptionMapper mapper = new WebApplicationExceptionMapper();
        mapper.uriInfo = createMockUriInfo("/clientes");

        WebApplicationException ex = new WebApplicationException("Método não permitido", 405);
        Response response = mapper.toResponse(ex);

        assertEquals(405, response.getStatus());
        assertEquals(ProblemDetails.MEDIA_TYPE, response.getMediaType().toString());

        ProblemDetails entity = (ProblemDetails) response.getEntity();
        assertEquals(URI.create("urn:problem-type:http-405"), entity.getType());
        assertEquals(405, entity.getStatus());
        assertEquals("Método não permitido", entity.getDetail());
    }

    @Test
    void testGeneralExceptionMapper() {
        GeneralExceptionMapper mapper = new GeneralExceptionMapper();
        mapper.uriInfo = createMockUriInfo("/clientes");

        RuntimeException ex = new RuntimeException("Erro inesperado no banco de dados");
        Response response = mapper.toResponse(ex);

        assertEquals(500, response.getStatus());
        assertEquals(ProblemDetails.MEDIA_TYPE, response.getMediaType().toString());

        ProblemDetails entity = (ProblemDetails) response.getEntity();
        assertEquals(URI.create("urn:problem-type:internal-server-error"), entity.getType());
        assertEquals("Erro interno no servidor", entity.getTitle());
        assertEquals("Ocorreu um erro interno inesperado no servidor.", entity.getDetail());
    }

    @Test
    void testConstraintViolationExceptionMapper() {
        ConstraintViolationExceptionMapper mapper = new ConstraintViolationExceptionMapper();
        mapper.uriInfo = createMockUriInfo("/clientes");

        TestConstraintViolation cv = new TestConstraintViolation("email", "Formato de email inválido.");
        ConstraintViolationException ex = new ConstraintViolationException(Set.of(cv));

        Response response = mapper.toResponse(ex);

        assertEquals(400, response.getStatus());
        assertEquals(ProblemDetails.MEDIA_TYPE, response.getMediaType().toString());

        ProblemDetails entity = (ProblemDetails) response.getEntity();
        assertEquals(URI.create("urn:problem-type:validation-error"), entity.getType());
        assertEquals("Erro de validação", entity.getTitle());
        assertEquals(400, entity.getStatus());
        assertNotNull(entity.getViolations());
        assertEquals(1, entity.getViolations().size());
        assertEquals("email", entity.getViolations().getFirst().field());
        assertEquals("Formato de email inválido.", entity.getViolations().getFirst().message());
    }

    private UriInfo createMockUriInfo(String path) {
        return (UriInfo) java.lang.reflect.Proxy.newProxyInstance(
                UriInfo.class.getClassLoader(),
                new Class<?>[]{UriInfo.class},
                (proxy, method, args) -> {
                    if ("getPath".equals(method.getName())) {
                        return path;
                    }
                    if ("getRequestUri".equals(method.getName())) {
                        return URI.create("http://localhost:8080" + path);
                    }
                    return null;
                }
        );
    }

    private static class TestConstraintViolation implements ConstraintViolation<Object> {
        private final String fieldName;
        private final String message;

        public TestConstraintViolation(String fieldName, String message) {
            this.fieldName = fieldName;
            this.message = message;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public String getMessageTemplate() {
            return null;
        }

        @Override
        public Object getRootBean() {
            return null;
        }

        @Override
        public Class<Object> getRootBeanClass() {
            return Object.class;
        }

        @Override
        public Object getLeafBean() {
            return null;
        }

        @Override
        public Object[] getExecutableParameters() {
            return new Object[0];
        }

        @Override
        public Object getExecutableReturnValue() {
            return null;
        }

        @Override
        public Path getPropertyPath() {
            return new Path() {
                @Override
                public Iterator<Node> iterator() {
                    return List.<Node>of(new Node() {
                        @Override
                        public String getName() {
                            return fieldName;
                        }

                        @Override
                        public boolean isInIterable() {
                            return false;
                        }

                        @Override
                        public Integer getIndex() {
                            return null;
                        }

                        @Override
                        public Object getKey() {
                            return null;
                        }

                        @Override
                        public ElementKind getKind() {
                            return ElementKind.PROPERTY;
                        }

                        @Override
                        public <T extends Node> T as(Class<T> nodeType) {
                            return null;
                        }

                        @Override
                        public String toString() {
                            return fieldName;
                        }
                    }).iterator();
                }

                @Override
                public String toString() {
                    return fieldName;
                }
            };
        }

        @Override
        public Object getInvalidValue() {
            return null;
        }

        @Override
        public jakarta.validation.metadata.ConstraintDescriptor<?> getConstraintDescriptor() {
            return null;
        }

        @Override
        public <U> U unwrap(Class<U> type) {
            return null;
        }
    }
}
