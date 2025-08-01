
package com.ctw.workstation.team;

import com.ctw.workstation.DTOs.Responses.ResponseTeamDTO;
import com.ctw.workstation.team.Service.TeamService;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.transaction.annotations.Rollback;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestHTTPEndpoint(TeamResource.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TeamResourceTest {

    @Inject
    TeamService teamService;

    @Test
    @DisplayName("Create Team")
    @Order(1)
    void createTeam() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "name": "string",
                          "product": "string",
                          "defaultLocation": "String",
                          "createdAt": "2022-03-10",
                          "modifiedAt": "2022-03-10"
                        }
                        """)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .assertThat()
                .body("id", Matchers.notNullValue());

        // Assertj can compare different objects (someDTO, entity)
        Optional<ResponseTeamDTO> teamDTO = teamService.findTeamById(Long.valueOf(1));

        Assertions.assertThat(teamDTO).isPresent();
        assertEquals(teamDTO.get().name(), "string");
        assertEquals(teamDTO.get().product(), "string");
        assertEquals(teamDTO.get().defaultLocation(), "String");

    }

    @Test
    @DisplayName("Get Teams")
    @Order(2)
    void getTeams() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("size()", Matchers.equalTo(1));
    }

    @Test
    @DisplayName("Get a team")
    @Order(3)
    void getTeam() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("{id}", 1)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("id", Matchers.equalTo(1));
    }

    @Test
    @DisplayName("Get a team with an invalid Id")
    @Order(4)
    void getInvalidTeam() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("{id}", 2)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);

        Optional<ResponseTeamDTO> teamDTO = teamService.findTeamById(Long.valueOf(2));
        Assertions.assertThat(teamDTO).isEmpty();

    }

    @Test
    @DisplayName("Update a team")
    @Order(5)
    void updateTeam() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "name": "updatedName",
                          "product": "updatedProduct"
                        }
                        """)
                .when()
                .put("{id}", 1)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("id", Matchers.equalTo(1))
                .body("name", Matchers.equalTo("updatedName"))
                .body("product", Matchers.equalTo("updatedProduct"))
                .body("defaultLocation", Matchers.equalTo("String"));

        Optional<ResponseTeamDTO> teamDTO = teamService.findTeamById(Long.valueOf(1));

        Assertions.assertThat(teamDTO).isPresent();
        assertEquals(teamDTO.get().name(), "updatedName");
        assertEquals(teamDTO.get().product(), "updatedProduct");
        assertEquals(teamDTO.get().defaultLocation(), "String");
    }

    @Test
    @DisplayName("Delete a team")
    @Order(6)
    void deleteTeam() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("{id}", 1)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        Assertions.assertThat(teamService.findTeamById(Long.valueOf(1)).isEmpty());

    }

    @Test
    @DisplayName("Delete a non existing team")
    @Order(7)
    void deleteNonExistingTeam() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("{id}", 1)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);

        Optional<ResponseTeamDTO> teamDTO = teamService.findTeamById(Long.valueOf(1));
        Assertions.assertThat(teamDTO).isEmpty();

    }

    @Test
    @DisplayName("Updating a invalid Team")
    @Order(8)
    void updateInvalidTeam() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "name": "updatedName",
                          "product": "updatedProduct"
                        }
                        """)
                .when()
                .put("{id}", 1)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);

        Optional<ResponseTeamDTO> teamDTO = teamService.findTeamById(Long.valueOf(1));
        Assertions.assertThat(teamDTO).isEmpty();

    }

}
