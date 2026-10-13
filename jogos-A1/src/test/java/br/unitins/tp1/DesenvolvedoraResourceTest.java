package br.unitins.tp1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.dto.DesenvolvedoraDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
class DesenvolvedoraResourceTest {

    @Test
    void testListarDesenvolvedoras() {
        given()
            .when().get("/desenvolvedoras")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    void testBuscarDesenvolvedoraPorId() {
        given()
            .when().get("/desenvolvedoras/1")
            .then()
                .statusCode(200)
                .body("id", is(1))
                .body("nome", is("CD Projekt Red"));
    }

    @Test
    void testInserirDesenvolvedora() {
        DesenvolvedoraDTO dto = new DesenvolvedoraDTO(
                "FromSoftware",
                "Japão",
                1986,
                "https://www.fromsoftware.jp"
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/desenvolvedoras")
            .then()
                .statusCode(201)
                .body("nome", is("FromSoftware"))
                .body("paisOrigem", is("Japão"));
    }

    @Test
    void testAtualizarDesenvolvedora() {
        DesenvolvedoraDTO dtoCriar = new DesenvolvedoraDTO(
                "Studio Para Atualizar",
                "Canadá",
                2015,
                "https://studioupdate.ca"
        );

        int id = given()
            .contentType(ContentType.JSON)
            .body(dtoCriar)
            .when().post("/desenvolvedoras")
            .then()
                .statusCode(201)
                .extract().path("id");

        DesenvolvedoraDTO dtoUpdate = new DesenvolvedoraDTO(
                "Studio Atualizado com Sucesso",
                "Canadá",
                2015,
                "https://studioupdate.ca"
        );

        given()
            .contentType(ContentType.JSON)
            .body(dtoUpdate)
            .when().put("/desenvolvedoras/" + id)
            .then()
                .statusCode(200)
                .body("id", is(id))
                .body("nome", is("Studio Atualizado com Sucesso"));
    }

    @Test
    void testDeletarDesenvolvedora() {
        DesenvolvedoraDTO dto = new DesenvolvedoraDTO(
                "Temp Studio",
                "Brasil",
                2020,
                "https://www.tempstudio.com.br"
        );

        int id = given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/desenvolvedoras")
            .then()
                .statusCode(201)
                .extract().path("id");

        given()
            .when().delete("/desenvolvedoras/" + id)
            .then()
                .statusCode(204);

        given()
            .when().get("/desenvolvedoras/" + id)
            .then()
                .statusCode(404);
    }
}
