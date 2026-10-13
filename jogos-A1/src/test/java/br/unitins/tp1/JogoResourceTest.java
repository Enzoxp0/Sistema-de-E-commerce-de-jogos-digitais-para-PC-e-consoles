package br.unitins.tp1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.dto.JogoDTO;
import br.unitins.tp1.dto.RequisitoSistemaDTO;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
class JogoResourceTest {

    @Test
    void testListarJogos() {
        given()
            .when().get("/jogos")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }

    @Test
    void testBuscarJogoPorId() {
        given()
            .when().get("/jogos/1")
            .then()
                .statusCode(200)
                .body("id", is(1))
                .body("titulo", is("The Witcher 3: Wild Hunt"))
                .body("desenvolvedora.nome", is("CD Projekt Red"));
    }

    @Test
    void testInserirJogo() {
        RequisitoSistemaDTO requisitos = new RequisitoSistemaDTO(
                "Windows 10 64-bit",
                "Intel Core i7-4770K",
                "16 GB",
                "NVIDIA GTX 1060 6GB",
                "45 GB"
        );

        JogoDTO dto = new JogoDTO(
                "Cyberpunk 2077",
                "RPG de ação em Night City",
                new BigDecimal("199.90"),
                1, // RPG
                1L, // CD Projekt Red
                List.of(1L, 2L, 3L), // PC, PS5, Xbox Series
                requisitos
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/jogos")
            .then()
                .statusCode(201)
                .body("titulo", is("Cyberpunk 2077"))
                .body("desenvolvedora.nome", is("CD Projekt Red"))
                .body("requisitoSistema.so", is("Windows 10 64-bit"));
    }

    @Test
    void testInserirJogoComGeneroInvalido() {
        RequisitoSistemaDTO requisitos = new RequisitoSistemaDTO(
                "Windows 10", "CPU", "8 GB", "GPU", "50 GB"
        );

        JogoDTO dto = new JogoDTO(
                "Jogo Teste",
                "Descrição",
                new BigDecimal("99.90"),
                999, // Gênero inválido
                1L,
                List.of(1L),
                requisitos
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/jogos")
            .then()
                .statusCode(400)
                .contentType("application/problem+json")
                .body("type", is("urn:problem-type:bad-request"))
                .body("title", is("Requisição inválida"));
    }

    @Test
    void testBuscarJogoPorIdInexistente() {
        given()
            .when().get("/jogos/999999")
            .then()
                .statusCode(404)
                .contentType("application/problem+json")
                .body("type", is("urn:problem-type:not-found"))
                .body("title", is("Recurso não encontrado"));
    }

    @Test
    void testAtualizarJogo() {
        RequisitoSistemaDTO requisitosCriar = new RequisitoSistemaDTO(
                "Windows 10", "Intel Core i5", "8 GB", "GTX 1060", "50 GB"
        );

        JogoDTO dtoCriar = new JogoDTO(
                "Jogo Para Atualizar",
                "Descrição inicial",
                new BigDecimal("100.00"),
                1,
                1L,
                List.of(1L),
                requisitosCriar
        );

        int id = given()
            .contentType(ContentType.JSON)
            .body(dtoCriar)
            .when().post("/jogos")
            .then()
                .statusCode(201)
                .extract().path("id");

        RequisitoSistemaDTO requisitosUpdate = new RequisitoSistemaDTO(
                "Windows 11", "Intel Core i7", "16 GB", "RTX 3070", "70 GB"
        );

        JogoDTO dtoUpdate = new JogoDTO(
                "Jogo Atualizado com Sucesso",
                "Versão aprimorada com Ray Tracing",
                new BigDecimal("159.90"),
                1,
                1L,
                List.of(1L, 2L),
                requisitosUpdate
        );

        given()
            .contentType(ContentType.JSON)
            .body(dtoUpdate)
            .when().put("/jogos/" + id)
            .then()
                .statusCode(200)
                .body("id", is(id))
                .body("titulo", is("Jogo Atualizado com Sucesso"))
                .body("preco", is(159.9F));
    }

    @Test
    void testDeletarJogo() {
        RequisitoSistemaDTO requisitos = new RequisitoSistemaDTO(
                "Win 10", "CPU", "4 GB", "GPU", "10 GB"
        );

        JogoDTO dto = new JogoDTO(
                "Jogo Para Deletar",
                "Jogo descartável de teste",
                new BigDecimal("49.90"),
                1,
                1L,
                List.of(1L),
                requisitos
        );

        int id = given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/jogos")
            .then()
                .statusCode(201)
                .extract().path("id");

        given()
            .when().delete("/jogos/" + id)
            .then()
                .statusCode(204);

        given()
            .when().get("/jogos/" + id)
            .then()
                .statusCode(404);
    }

    @Test
    void testBuscarJogosPorFiltros() {
        // Busca por Título
        given()
            .when().get("/jogos/search/titulo/Witcher")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));

        // Busca por Gênero (1 = RPG)
        given()
            .when().get("/jogos/search/genero/1")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));

        // Busca por Plataforma (1 = PC)
        given()
            .when().get("/jogos/search/plataforma/1")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));

        // Busca por Desenvolvedora (1 = CD Projekt Red)
        given()
            .when().get("/jogos/search/desenvolvedora/1")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));

        // Busca por Preço
        given()
            .when().get("/jogos/search/preco/350.00")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }
}
