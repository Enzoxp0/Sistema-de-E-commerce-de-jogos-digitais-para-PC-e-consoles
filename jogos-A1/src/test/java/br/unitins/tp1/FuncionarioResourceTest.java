package br.unitins.tp1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.dto.FuncionarioDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
class FuncionarioResourceTest {

    @Test
    void testListarFuncionarios() {
        given()
            .when().get("/funcionarios")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    void testBuscarFuncionarioPorId() {
        given()
            .when().get("/funcionarios/4")
            .then()
                .statusCode(200)
                .body("id", is(4))
                .body("matricula", is("FUNC-001"));
    }

    @Test
    void testInserirFuncionario() {
        FuncionarioDTO dto = new FuncionarioDTO(
                "Lucas Dev",
                "88877766655",
                "lucas.developer@jogos.com",
                "FUNC-999",
                "Analista de Testes",
                new BigDecimal("5500.00")
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/funcionarios")
            .then()
                .statusCode(201)
                .body("nome", is("Lucas Dev"))
                .body("matricula", is("FUNC-999"));
    }

    @Test
    void testAtualizarFuncionario() {
        FuncionarioDTO dtoCriar = new FuncionarioDTO(
                "Funcionario Para Atualizar",
                "99911122233",
                "func.update@jogos.com",
                "FUNC-UPD",
                "Analista Jr",
                new BigDecimal("3500.00")
        );

        int id = given()
            .contentType(ContentType.JSON)
            .body(dtoCriar)
            .when().post("/funcionarios")
            .then()
                .statusCode(201)
                .extract().path("id");

        FuncionarioDTO dtoUpdate = new FuncionarioDTO(
                "Funcionario Atualizado Sucesso",
                "99911122233",
                "func.update@jogos.com",
                "FUNC-UPD",
                "Analista Pleno",
                new BigDecimal("5500.00")
        );

        given()
            .contentType(ContentType.JSON)
            .body(dtoUpdate)
            .when().put("/funcionarios/" + id)
            .then()
                .statusCode(200)
                .body("id", is(id))
                .body("nome", is("Funcionario Atualizado Sucesso"))
                .body("cargo", is("Analista Pleno"));
    }

    @Test
    void testDeletarFuncionario() {
        FuncionarioDTO dto = new FuncionarioDTO(
                "Funcionario Temp",
                "11100099988",
                "temp.func@jogos.com",
                "FUNC-TMP",
                "Estagiário",
                new BigDecimal("2000.00")
        );

        int id = given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/funcionarios")
            .then()
                .statusCode(201)
                .extract().path("id");

        given()
            .when().delete("/funcionarios/" + id)
            .then()
                .statusCode(204);

        given()
            .when().get("/funcionarios/" + id)
            .then()
                .statusCode(404);
    }
}
