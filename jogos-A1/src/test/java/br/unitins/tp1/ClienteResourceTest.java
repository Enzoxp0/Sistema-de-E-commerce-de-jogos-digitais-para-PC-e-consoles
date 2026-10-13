package br.unitins.tp1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.dto.ClienteDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
class ClienteResourceTest {

    @Test
    void testListarClientes() {
        given()
            .when().get("/clientes")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    void testBuscarClientePorId() {
        given()
            .when().get("/clientes/1")
            .then()
                .statusCode(200)
                .body("id", is(1))
                .body("nickname", is("DarkKnight99"));
    }

    @Test
    void testInserirCliente() {
        ClienteDTO dto = new ClienteDTO(
                "Novo Gamer",
                "99988877766",
                "novo.gamer@email.com",
                "ProGamer999",
                "63988776655"
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/clientes")
            .then()
                .statusCode(201)
                .body("nome", is("Novo Gamer"))
                .body("nickname", is("ProGamer999"));
    }

    @Test
    void testInserirClienteCpfDuplicado() {
        ClienteDTO dto = new ClienteDTO(
                "Gabriela Costa",
                "33344455566",
                "gabriela.costa@email.com",
                "SpeedQueen",
                "63993334466"
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/clientes")
            .then()
                .statusCode(400)
                .contentType("application/problem+json")
                .body("type", is("urn:problem-type:bad-request"))
                .body("title", is("Requisição inválida"))
                .body("status", is(400))
                .body("detail", is("Já existe uma pessoa cadastrada com o CPF informado (33344455566)."))
                .body("instance", is("/clientes"));
    }

    @Test
    void testInserirClienteComDadosInvalidos() {
        ClienteDTO dto = new ClienteDTO(
                "",
                "123",
                "email-invalido",
                "",
                "123"
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/clientes")
            .then()
                .statusCode(400)
                .contentType("application/problem+json")
                .body("type", is("urn:problem-type:validation-error"))
                .body("title", is("Erro de validação"))
                .body("status", is(400))
                .body("instance", is("/clientes"))
                .body("violations.size()", greaterThanOrEqualTo(1));
    }

    @Test
    void testBuscarClientePorIdInexistente() {
        given()
            .when().get("/clientes/999999")
            .then()
                .statusCode(404)
                .contentType("application/problem+json")
                .body("type", is("urn:problem-type:not-found"))
                .body("title", is("Recurso não encontrado"))
                .body("status", is(404))
                .body("detail", is("Cliente não encontrado com o ID: 999999"))
                .body("instance", is("/clientes/999999"));
    }

    @Test
    void testAtualizarCliente() {
        ClienteDTO dtoCriar = new ClienteDTO(
                "Cliente Para Atualizar",
                "66655544433",
                "update.client@email.com",
                "GamerToUpdate",
                "63988112233"
        );

        int id = given()
            .contentType(ContentType.JSON)
            .body(dtoCriar)
            .when().post("/clientes")
            .then()
                .statusCode(201)
                .extract().path("id");

        ClienteDTO dtoUpdate = new ClienteDTO(
                "Cliente Atualizado Sucesso",
                "66655544433",
                "update.client@email.com",
                "GamerUpdated",
                "63999998888"
        );

        given()
            .contentType(ContentType.JSON)
            .body(dtoUpdate)
            .when().put("/clientes/" + id)
            .then()
                .statusCode(200)
                .body("id", is(id))
                .body("nome", is("Cliente Atualizado Sucesso"))
                .body("nickname", is("GamerUpdated"));
    }

    @Test
    void testDeletarCliente() {
        ClienteDTO dto = new ClienteDTO(
                "Cliente Temp",
                "77766655544",
                "temp.cliente@email.com",
                "TempGamer",
                "63988770000"
        );

        int id = given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/clientes")
            .then()
                .statusCode(201)
                .extract().path("id");

        given()
            .when().delete("/clientes/" + id)
            .then()
                .statusCode(204);

        given()
            .when().get("/clientes/" + id)
            .then()
                .statusCode(404);
    }
}
