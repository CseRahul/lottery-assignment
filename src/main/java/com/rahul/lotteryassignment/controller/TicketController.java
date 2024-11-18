package com.rahul.lotteryassignment.controller;

import com.rahul.lotteryassignment.constant.ErrorCode;
import com.rahul.lotteryassignment.dto.LotteryApiResponse;
import com.rahul.lotteryassignment.dto.Ticket;
import com.rahul.lotteryassignment.exception.CustomException;
import com.rahul.lotteryassignment.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Rahul Kumar, reader.rahul@gmail.com
 * REST controller for managing lottery tickets.
 */
@Tag(name = "Lottery Ticket API", description = "APIs for managing lottery tickets")
@RestController
@RequestMapping("/ticket")
@Validated
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * Creates a new ticket with the specified number of lines.
     *
     * @param numberOfLines Number of lines (must be greater than or equal to 1)
     * @return Created ticket with details
     */
    @Operation(summary = "Create a new ticket", description = "Creates a ticket with the specified number of lines.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<LotteryApiResponse<Ticket>> createTicket(
            @RequestParam("numberOfLines")
            @Min(value = 1, message = "Number of lines must be at least 1") int numberOfLines) {
        Ticket ticket = ticketService.createTicket(numberOfLines);
        if (ticket == null) {
            throw new CustomException(ErrorCode.TICKET_NOT_CREATED,
                    "Unable to create ticket!",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(LotteryApiResponse.success("Ticket created successfully!", ticket));
    }

    /**
     * Retrieves all tickets in the system.
     *
     * @return List of all tickets
     */
    @Operation(summary = "Get all tickets", description = "Retrieves a list of all tickets in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tickets retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No tickets found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<LotteryApiResponse<List<Ticket>>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();

        if (CollectionUtils.isEmpty(tickets)) {
            throw new CustomException(ErrorCode.TICKET_NOT_FOUND,
                    "No tickets found!",
                    HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(LotteryApiResponse.success("Tickets retrieved successfully!", tickets));
    }

    /**
     * Retrieves a ticket by its ID.
     *
     * @param id Ticket ID
     * @return The requested ticket
     */
    @Operation(summary = "Get ticket by ID", description = "Retrieves a specific ticket using its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LotteryApiResponse<Ticket>> getTicketById(@PathVariable Integer id) {
        Ticket ticket = ticketService.getTicketById(id);

        if (ticket == null) {
            throw new CustomException(ErrorCode.TICKET_NOT_FOUND,
                    "Ticket not found for ID: " + id,
                    HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(LotteryApiResponse.success("Ticket retrieved successfully!", ticket));
    }

    /**
     * Adds lines to an existing ticket.
     *
     * @param id            Ticket ID
     * @param numberOfLines Number of lines to add (minimum value: 1)
     * @return Updated ticket
     */
    @Operation(summary = "Add lines to ticket", description = "Adds additional lines to an existing ticket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket updated successfully"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LotteryApiResponse<Ticket>> addLinesToTicket(
            @PathVariable Integer id,
            @RequestParam("numberOfLines")
            @Min(value = 1, message = "Number of lines must be at least 1") int numberOfLines) {
        Ticket ticket = ticketService.addLines(id, numberOfLines);
        if (ticket == null) {
            throw new CustomException(ErrorCode.TICKET_NOT_FOUND,
                    "Ticket not found for ID: " + id,
                    HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(LotteryApiResponse.success("Ticket updated successfully!", ticket));
    }

    /**
     * Checks the status of a ticket and sorts its lines by result.
     *
     * @param id Ticket ID
     * @return List of lines with results
     */
    @Operation(summary = "Check ticket status", description = "Checks the status of a ticket and sorts its lines by result.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket status retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Lines not found for ticket"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/status/{id}")
    public ResponseEntity<LotteryApiResponse<Ticket>> checkTicketStatus(@PathVariable Integer id) {
        Ticket ticket = ticketService.checkTicketStatus(id);
        if ((null == ticket) || CollectionUtils.isEmpty(ticket.getLines())) {
            throw new CustomException(ErrorCode.TICKET_NOT_FOUND,
                    "Lines not found for ticket ID: " + id,
                    HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(LotteryApiResponse.success("Ticket status retrieved successfully!", ticket));
    }
}




