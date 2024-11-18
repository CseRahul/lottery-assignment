package com.rahul.lotteryassignment;

import com.rahul.lotteryassignment.dto.Line;
import com.rahul.lotteryassignment.dto.Ticket;
import com.rahul.lotteryassignment.repository.TicketRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class LotteryApiTest {
    
    @BeforeEach
    void setup() {
        // Set base URI for the API
        RestAssured.baseURI = "http://localhost:8080/ticket"; // Adjust this if the port or URL changes
    }

    /**
     * Test case to create a new ticket with 3 lines.
     * Verifies the ticket is created and the ID is returned.
     */
    @Test
    void testCreateTicket() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("numberOfLines", 3)
                .when()
                .post()
                .then()
                .statusCode(201)  // Expect HTTP 201 Created
                .body("message", equalTo("Ticket created successfully!"))  // Check success message
                .body("data.id", notNullValue())  // Ensure ticket ID is returned
                .body("data.lines.size()", is(3));  // Ensure there are 3 lines in the ticket
    }

    /**
     * Test case to retrieve all tickets.
     * Verifies a list of tickets is returned.
     */
    @Test
    void testGetAllTickets() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0))  // Ensure there is at least one ticket
                .body("message", equalTo("Tickets retrieved successfully!"))
                .body("data", not(empty()));  // Ensure there are tickets in the response

    }

    /**
     * Test case to retrieve a specific ticket by ID.
     * Verifies the correct ticket is returned.
     */
    @Test
    void testGetTicketById() {
        int ticketId = getTicketByIndex(0).getId();
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/" + ticketId)
                .then()
                .statusCode(200)
                .body("data.id", equalTo(ticketId));  // Ensure the returned ticket ID matches
    }

    /**
     * Test case to add lines to an existing ticket.
     */
    @Test
    void testAddLinesToTicket() {
        int ticketId = getTicketByIndex(0).getId();
        int numberOfLines = 2;

        given()
                .contentType(ContentType.JSON)
                .queryParam("numberOfLines", numberOfLines)
                .when()
                .put("/" + ticketId)
                .then()
                .statusCode(200)  // Expect HTTP 200 OK
                .body("message", equalTo("Ticket updated successfully!"))
                .body("data.id", equalTo(ticketId))  // Ensure the ticket ID remains the same
                .body("data.lines.size()", is(5));  // Ensure there are 5 lines after update (original 3 + 2)
    }

    @Test
    void testCheckTicketStatus() {
        Ticket ticket = new Ticket();
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(0, 1, 1));//10
        lines.add(new Line(1, 1, 1));//5
        lines.add(new Line(0, 1, 2));//1
        lines.add(new Line(0, 1, 0));//0
        ticket.addLines(lines);
        TicketRepository.tickets.put(ticket.getId(), ticket);


        given()
                .contentType(ContentType.JSON)
                .when()
                .put("/status/" + ticket.getId())
                .then()
                
                .statusCode(200)
                .body("data.lines[0].result", equalTo(10))  // First line result should be 10
                .body("data.lines[1].result", equalTo(5))   // Second line result should be 5
                .body("data.lines[2].result", equalTo(1))   // Third line result should be 1
                .body("data.lines[3].result", equalTo(0));  // Third line result should be 1
    }


    /**
     * Test case to try adding lines after the ticket has been checked.
     * Verifies the request is rejected with an error.
     */
    @Test
    void testAddLinesAfterCheck() {
        Ticket ticket = new Ticket();
        List<Line> lines = new ArrayList<>();
        lines.add(new Line(0, 1, 1));//10
        lines.add(new Line(1, 1, 1));//5
        ticket.addLines(lines);
        TicketRepository.tickets.put(ticket.getId(), ticket);

        given()
                .contentType(ContentType.JSON)
                .when()
                .put("/status/" + ticket.getId())
                .then()
                
                .statusCode(200)
                .body("data.lines[0].result", equalTo(10))  // First line result should be 10
                .body("data.lines[1].result", equalTo(5)); // Second line result should be 5

        given()
                .contentType(ContentType.JSON)
                .queryParam("numberOfLines", 1)
                .when()
                .put("/" + ticket.getId())
                .then()
                
                .statusCode(409)  // Expect HTTP 200 OK
                .body("message", equalTo("[ERR-003] Not Modifiable"))
                .body("data", equalTo(null)); // Ensure the ticket ID remains the same
    }


    /**
     * Test case to validate ticket creation with invalid data.
     * Verifies the creation fails with validation errors.
     */
    @Test
    void testCreateTicketWithInvalidData() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("numberOfLines", -1)
                .when()
                .post()
                .then()
                .statusCode(400)  // Expecting a bad request
                .body("message", containsString("[ERR-002:Invalid Input] - Number of lines must be at least 1"));
    }

    /**
     * Test case to try checking the status of a non-existent ticket.
     * Verifies the request is rejected with a 404 error.
     */
    @Test
    void testCheckStatusForNonExistentTicket() {
        int nonExistentTicketId = 9999;

        given()
                .contentType(ContentType.JSON)
                .when()
                .put("/status/" + nonExistentTicketId)
                .then()
                
                .statusCode(404)  // Expecting a not found error
                .body("message", containsString("[ERR-001] Ticket Not Found"));
    }

    /**
     * Convenience method to get the existing ticket
     */
    private Ticket getTicketByIndex(int index) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().jsonPath().getObject("data["+index+"]", Ticket.class);
    }

}
