/**
package com.ctw.workstation.rackAsset.entity;

import com.ctw.workstation.DTOs.Responses.ResponseRackAssetDTO;
import com.ctw.workstation.DTOs.Responses.ResponseRackDTO;
import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.rack.entity.Status;
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
@TestHTTPEndpoint(RackAssetResource.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RackAssetResourceTest {

    @Inject
    RackAssetService rackAssetService;

    Team team;
    Rack rack;

    @BeforeAll
    @Transactional
    void setup() {

        team = new Team();
        team.name = "Team Name";
        team.defaultLocation = "Default Location";
        team.product = "Product Name";
        team.persist();

        rack = new Rack();
        rack.status = Status.ACTIVE;
        rack.teamId = team.id;
        rack.serialNumber = "1234";
        rack.defaultLocation = "Location";
        rack.persist();
    }

    @AfterAll
    @Transactional
    void afterAll() {
        team.delete();
        rack.delete();
    }

    @Test
    @DisplayName("Create rack asset")
    @Order(1)
    void createRackAsset() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                                "asset_tag": "string",
                                    "rackId": 1
                        }
                        """)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .assertThat()
                .body("id", Matchers.notNullValue());

        Optional<ResponseRackAssetDTO> rackAssetDTO = rackAssetService.findRackAssetByRackId(Long.valueOf(1));

        Assertions.assertThat(rackAssetDTO).isPresent();
        assertEquals(rackAssetDTO.get().asset_tag(), "string");
        assertEquals(rackAssetDTO.get().rackId(), 1);
    }

    @Test
    @DisplayName("Get all rack Assets")
    @Order(2)
    void getRackAssets() {

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
    @DisplayName("Get a rack Asset")
    @Order(3)
    void getRackAsset() {

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
    @DisplayName("Get a Rack Asset with an invalid Id")
    @Order(4)
    void getInvalidRackAsset() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("{id}", 2)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_NOT_FOUND);

        Optional<ResponseRackAssetDTO> rackAssetDTO = rackAssetService.findRackAssetByRackId(Long.valueOf(2));
        Assertions.assertThat(rackAssetDTO).isEmpty();
    }

    @Test
    @DisplayName("Update a Rack")
    @Order(5)
    void updateRackAsset() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "asset_tag": "newtag1"
                        }
                        """)
                .when()
                .put("{id}", 1)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_OK)
                .assertThat()
                .body("id", Matchers.equalTo(1))
                .body("asset_tag", Matchers.equalTo("newtag1"))
                .body("rackId", Matchers.equalTo(1));

        Optional<ResponseRackAssetDTO> rackAssetDTO = rackAssetService.findRackAssetByRackId(Long.valueOf(1));

        Assertions.assertThat(rackAssetDTO).isPresent();
        assertEquals(rackAssetDTO.get().asset_tag(), "newtag1");
        assertEquals(rackAssetDTO.get().rackId(), 1);
    }

    @Test
    @DisplayName("Delete a Rack Asset")
    @Order(6)
    void deleteRackAsset() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("{id}", 1)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_NO_CONTENT);

        Assertions.assertThat(rackAssetService.findRackAssetByRackId(Long.valueOf(1)).isEmpty());

    }

    @Test
    @DisplayName("Delete a non existing Rack Asset")
    @Order(7)
    void deleteNonExistingRackAsset() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("{id}", 1)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_NOT_FOUND);

        Optional<ResponseRackAssetDTO> rackAssetDTO = rackAssetService.findRackAssetByRackId(Long.valueOf(1));
        Assertions.assertThat(rackAssetDTO).isEmpty();
    }

    @Test
    @DisplayName("Updating a invalid rack Asset")
    @Order(8)
    void updateInvalidRackAsset() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "asset_tag": "newtag1"
                        }
                        """)
                .when()
                .put("{id}", 1)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_NOT_FOUND);

        Optional<ResponseRackAssetDTO> rackAssetDTO = rackAssetService.findRackAssetByRackId(Long.valueOf(1));
        Assertions.assertThat(rackAssetDTO).isEmpty();
    }

}**/