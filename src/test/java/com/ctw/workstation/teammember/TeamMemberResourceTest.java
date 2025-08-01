/**
package com.ctw.workstation.teammember;

import com.ctw.workstation.DTOs.Responses.ResponseTeamMemberDTO;
import com.ctw.workstation.team.entity.Team;
import com.ctw.workstation.teammember.Service.TeamMemberService;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.transaction.annotations.Rollback;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.apache.hc.core5.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestHTTPEndpoint(TeamMemberResource.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TeamMemberResourceTest {

    @Inject
    TeamMemberService teamMemberService;

    Team team;

    @BeforeAll
    @Transactional
    void beforeEach() {
        team = new Team();
        team.name = "Team Name";
        team.defaultLocation = "Default Location";
        team.product = "Product Name";
        team.persist();
    }

    @AfterAll
    @Transactional
    void afterAll() {
        team.delete();
    }

    @Test
    @DisplayName("Create Team Member")
    @Order(1)
    void createTeamMember() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "teamId": 1,
                          "ctwId": "string",
                          "name": "string",
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

        Optional<ResponseTeamMemberDTO> teamMemberDTO = teamMemberService.findTeamMemberById(Long.valueOf(1));
        Assertions.assertThat(teamMemberDTO).isPresent();
        assertEquals(teamMemberDTO.get().name(), "string");
        assertEquals(teamMemberDTO.get().ctwId(), "string");

    }

    @Test
    @DisplayName("Get all Team Members")
    @Order(2)
    void getTeamMembers() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_OK)
                .assertThat()
                .body("size()", Matchers.equalTo(1));
    }

    @Test
    @DisplayName("Get a team Member")
    @Order(3)
    void getTeamMember() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("{id}", 1)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_OK)
                .assertThat()
                .body("id", Matchers.equalTo(1));
    }

    @Test
    @DisplayName("Get a team member with an invalid Id")
    @Order(4)
    void getInvalidTeamMember() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("{id}", 2)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_NOT_FOUND);

        Optional<ResponseTeamMemberDTO> teamMemberDTO = teamMemberService.findTeamMemberById(Long.valueOf(2));
        Assertions.assertThat(teamMemberDTO).isEmpty();

    }

    @Test
    @DisplayName("Update a team member")
    @Order(5)
    void updateTeamMember() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "ctwId": "ctwid",
                          "name": "updatedName"
                        }
                        """)
                .when()
                .put("{id}", 1)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_OK)
                .assertThat()
                .body("id", Matchers.equalTo(1))
                .body("ctwId", Matchers.equalTo("ctwid"))
                .body("name", Matchers.equalTo("updatedName"));

        Optional<ResponseTeamMemberDTO> teamMemberDTO = teamMemberService.findTeamMemberById(Long.valueOf(1));

        Assertions.assertThat(teamMemberDTO).isPresent();
        assertEquals(teamMemberDTO.get().name(), "updatedName");
        assertEquals(teamMemberDTO.get().ctwId(), "ctwid");
    }


    @Test
    @DisplayName("Delete a team Member")
    @Order(6)
    void deleteTeam() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("{id}", 1)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_NO_CONTENT);

        Assertions.assertThat(teamMemberService.findTeamMemberById(Long.valueOf(1)).isEmpty());

    }

    @Test
    @DisplayName("Delete a non existing team member")
    @Order(7)
    void deleteNonExistingTeam() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("{id}", 1)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_NOT_FOUND);

        Optional<ResponseTeamMemberDTO> teamMemberDTO = teamMemberService.findTeamMemberById(Long.valueOf(1));
        Assertions.assertThat(teamMemberDTO).isEmpty();
    }


    @Test
    @DisplayName("Updating a invalid Team Member")
    @Order(8)
    void updateInvalidTeam() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "ctwId": "ctwid",
                          "name": "updatedName"
                        }
                        """)
                .when()
                .put("{id}", 1)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_NOT_FOUND);

        Optional<ResponseTeamMemberDTO> teamMemberDTO = teamMemberService.findTeamMemberById(Long.valueOf(1));
        Assertions.assertThat(teamMemberDTO).isEmpty();

    }

}
 **/