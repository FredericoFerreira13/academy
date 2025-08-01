/**

package com.ctw.workstation.rack;

import com.ctw.workstation.DTOs.Responses.ResponseRackDTO;
import com.ctw.workstation.rack.Service.RackService;
import com.ctw.workstation.team.entity.Team;
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
@TestHTTPEndpoint(RackResource.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RackResourceTest {

    @Inject
    RackService rackService;
    Team team;

    @BeforeAll
    @Transactional
    void beforeAll() {
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
    @DisplayName("Create Rack")
    @Order(1)
    void createRack() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "status": "ACTIVE",
                          "serialNumber": "string",
                          "teamId": 1,
                          "defaultLocation": "string"
                        }
                        """)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .assertThat()
                .body("id", Matchers.notNullValue());

        Optional<ResponseRackDTO> rackDTO = rackService.findRackById(Long.valueOf(1));

        Assertions.assertThat(rackDTO).isPresent();
        assertEquals(rackDTO.get().serialNumber(), "string");
        assertEquals(rackDTO.get().status(), "ACTIVE");
        assertEquals(rackDTO.get().defaultLocation(), "string");

    }

    @Test
    @DisplayName("Get all Racks")
    @Order(2)
    void getRacks() {

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
    @DisplayName("Get a Rack")
    @Order(3)
    void getRack() {

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
    @DisplayName("Get a Rack with an invalid Id")
    @Order(4)
    void getInvalidRack() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("{id}", 2)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_NOT_FOUND);

        Optional<ResponseRackDTO> rackDTO = rackService.findRackById(Long.valueOf(2));
        Assertions.assertThat(rackDTO).isEmpty();
    }

    @Test
    @DisplayName("Update a Rack")
    @Order(5)
    void updateRack() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "status": "RETURNED",
                          "defaultLocation": "Location"
                        }
                        """)
                .when()
                .put("{id}", 1)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_OK)
                .assertThat()
                .body("id", Matchers.equalTo(1))
                .body("status", Matchers.equalTo("RETURNED"))
                .body("defaultLocation", Matchers.equalTo("Location"));

        Optional<ResponseRackDTO> rackDTO = rackService.findRackById(Long.valueOf(1));

        Assertions.assertThat(rackDTO).isPresent();
        assertEquals(rackDTO.get().status(), "RETURNED");
        assertEquals(rackDTO.get().defaultLocation(), "Location");
    }

    @Test
    @DisplayName("Delete a Rack")
    @Order(6)
    void deleteRack() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("{id}", 1)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_NO_CONTENT);

        Assertions.assertThat(rackService.findRackById(Long.valueOf(1)).isEmpty());

    }

    @Test
    @DisplayName("Delete a non existing Rack")
    @Order(7)
    void deleteNonExistingRack() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("{id}", 1)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_NOT_FOUND);

        Optional<ResponseRackDTO> rackDTO = rackService.findRackById(Long.valueOf(1));
        Assertions.assertThat(rackDTO).isEmpty();
    }

    @Test
    @DisplayName("Updating a invalid rack")
    @Order(8)
    void updateInvalidRack() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "status": "RETURNED",
                          "defaultLocation": "Location"
                        }
                        """)
                .when()
                .put("{id}", 1)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_NOT_FOUND);

        Optional<ResponseRackDTO> rackDTO = rackService.findRackById(Long.valueOf(1));
        Assertions.assertThat(rackDTO).isEmpty();
    }

}
 **/