package br.unitins.tp1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.dto.PlataformaDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
class PlataformaResourceTest {

    @Test
    void testListarPlataformas() {
        given()
            .when().get("/plataformas")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    void testBuscarPlataformaPorId() {
        given()
            .when().get("/plataformas/1")
            .then()
                .statusCode(200)
                .body("id", is(1))
                .body("nome", is("PC"));
    }

    @Test
    void testInserirPlataforma() {
        PlataformaDTO dto = new PlataformaDTO("Steam Deck", "Valve");

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/plataformas")
            .then()
                .statusCode(201)
                .body("nome", is("Steam Deck"))
                .body("fabricante", is("Valve"));
    }

    @Test
    void testInserirPlataformaComNomeDuplicado() {
        PlataformaDTO dto = new PlataformaDTO("PC", "Microsoft");

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/plataformas")
            .then()
                .statusCode(400);
    }

    @Test
    void testAtualizarPlataforma() {
        PlataformaDTO dtoCriar = new PlataformaDTO("Plataforma Para Atualizar", "Fabricante A");

        int id = given()
            .contentType(ContentType.JSON)
            .body(dtoCriar)
            .when().post("/plataformas")
            .then()
                .statusCode(201)
                .extract().path("id");

        PlataformaDTO dtoUpdate = new PlataformaDTO("Plataforma Atualizada Sucesso", "Fabricante B");

        given()
            .contentType(ContentType.JSON)
            .body(dtoUpdate)
            .when().put("/plataformas/" + id)
            .then()
                .statusCode(200)
                .body("id", is(id))
                .body("nome", is("Plataforma Atualizada Sucesso"));
    }

    @Test
    void testDeletarPlataforma() {
        PlataformaDTO dto = new PlataformaDTO("Atari 2600", "Atari");

        int id = given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/plataformas")
            .then()
                .statusCode(201)
                .extract().path("id");

        given()
            .when().delete("/plataformas/" + id)
            .then()
                .statusCode(204);

        given()
            .when().get("/plataformas/" + id)
            .then()
                .statusCode(404);
    }
}
