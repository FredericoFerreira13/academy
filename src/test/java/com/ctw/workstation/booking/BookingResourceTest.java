/**
package com.ctw.workstation.booking;

import com.ctw.workstation.DTOs.Responses.ResponseBookingDTO;
import com.ctw.workstation.DTOs.Responses.ResponseRackDTO;
import com.ctw.workstation.DTOs.Responses.ResponseTeamDTO;
import com.ctw.workstation.rack.RackResource;
import com.ctw.workstation.rack.entity.Rack;
import com.ctw.workstation.rack.entity.Status;
import com.ctw.workstation.team.entity.Team;
import com.ctw.workstation.teammember.entity.TeamMember;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestHTTPEndpoint(BookingResource.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookingResourceTest {

    @Inject
    BookingService bookingService;

    Team team;
    TeamMember teamMember;
    Rack rack;

    @BeforeAll
    @Transactional
    void beforeAll() {
        team = new Team();
        team.name = "Team Name";
        team.defaultLocation = "Default Location";
        team.product = "Product Name";
        team.persist();

        teamMember = new TeamMember();
        teamMember.teamId = team.id;
        teamMember.ctwId = "ctwid";
        teamMember.name = "Team Member Name";
        teamMember.persist();

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
        teamMember.delete();
        rack.delete();
    }

    @Test
    @DisplayName("Create Booking")
    @Order(1)
    void createBooking() {

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "rackId": 1,
                          "requesterId": 1
                        }
                        """)
                .when()
                .post()
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .assertThat()
                .body("id", Matchers.notNullValue());

        Optional<ResponseBookingDTO> bookingDTO = bookingService.findBookingById(Long.valueOf(1));
        Assertions.assertThat(bookingDTO).isPresent();
        assertEquals(bookingDTO.get().rackId(), 1);
        assertEquals(bookingDTO.get().requesterId(), 1);

    }

    @Test
    @DisplayName("Get all Bookings")
    @Order(2)
    void getBookings() {

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
    @DisplayName("Get a Booking")
    @Order(3)
    void getBooking() {

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
    @DisplayName("Get a Booking with an invalid Id")
    @Order(4)
    void getInvalidBooking() {

        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get("{id}", 2)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_NOT_FOUND);

        Optional<ResponseBookingDTO> bookingDTO = bookingService.findBookingById(Long.valueOf(2));
        Assertions.assertThat(bookingDTO).isEmpty();
    }

    /**
     * @Test
     * @DisplayName("Update a Booking")
     * @Order(5) void updateBooking() {
     * RestAssured.given()
     * .contentType(ContentType.JSON)
     * .body("""
     * {
     * "status": "RETURNED",
     * "defaultLocation": "Location"
     * }
     * """)
     * .when()
     * .put("{id}", 1)
     * .then()
     * .assertThat()
     * .statusCode(org.apache.http.HttpStatus.SC_OK)
     * .assertThat()
     * .body("id", Matchers.equalTo(1))
     * .body("status", Matchers.equalTo("RETURNED"))
     * .body("defaultLocation", Matchers.equalTo("Location"));
     * <p>
     * Optional<ResponseBookingDTO> rackDTO = bookingService.findBookingById(Long.valueOf(1));
     * <p>
     * Assertions.assertThat(rackDTO).isPresent();
     * assertEquals(rackDTO.get().status(), "RETURNED");
     * assertEquals(rackDTO.get().defaultLocation(), "Location");
     * }


    @Test
    @DisplayName("Delete a Booking")
    @Order(6)
    void deleteBooking() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("{id}", 1)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_NO_CONTENT);

        Assertions.assertThat(bookingService.findBookingById(Long.valueOf(1)).isEmpty());

    }

    @Test
    @DisplayName("Delete a non existing Booking")
    @Order(7)
    void deleteNonExistingBooking() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .delete("{id}", 1)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_NOT_FOUND);

        Optional<ResponseBookingDTO> bookingDTO = bookingService.findBookingById(Long.valueOf(1));
        Assertions.assertThat(bookingDTO).isEmpty();
    }

    @Test
    @DisplayName("Updating a invalid Booking")
    @Order(8)
    void updateInvalidRack() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "rackId": 1,
                          "requesterId": 1
                        }
                        """)
                .when()
                .put("{id}", 1)
                .then()
                .assertThat()
                .statusCode(org.apache.http.HttpStatus.SC_NOT_FOUND);

        Optional<ResponseBookingDTO> bookingDTO = bookingService.findBookingById(Long.valueOf(1));
        Assertions.assertThat(bookingDTO).isEmpty();
    }

}
 **/