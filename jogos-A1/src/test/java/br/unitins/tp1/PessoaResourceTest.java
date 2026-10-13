package br.unitins.tp1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class PessoaResourceTest {

    @Test
    void testListarPessoas() {
        given()
            .when().get("/pessoas")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    void testBuscarPessoaPorId() {
        given()
            .when().get("/pessoas/1")
            .then()
                .statusCode(200)
                .body("id", is(1))
                .body("nome", is("Lucas Silva"))
                .body("tipo", is("CLIENTE"));
    }

    @Test
    void testBuscarPessoaPorIdInexistente() {
        given()
            .when().get("/pessoas/999999")
            .then()
                .statusCode(404)
                .contentType("application/problem+json")
                .body("type", is("urn:problem-type:not-found"))
                .body("title", is("Recurso não encontrado"))
                .body("detail", is("Pessoa não encontrada com o ID: 999999"));
    }
}
