package com.rahul.lotteryassignment.service;

import com.rahul.lotteryassignment.constant.ErrorCode;
import com.rahul.lotteryassignment.dto.Line;
import com.rahul.lotteryassignment.dto.Ticket;
import com.rahul.lotteryassignment.exception.CustomException;
import com.rahul.lotteryassignment.repository.TicketRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * @author Rahul Kumar, reader.rahul@gmail.com
 * Service class for managing lottery tickets.
 */
@Service
public class TicketService {

    /**
     * Creates a new ticket with the specified number of lines.
     *
     * @param lineCount Number of lines to be added to the ticket.
     * @return The created {@link Ticket} object.
     */
    public Ticket createTicket(int lineCount) {
        Ticket ticket = new Ticket();
        ticket.addLines(generateLines(lineCount));
        TicketRepository.tickets.put(ticket.getId(), ticket);
        return ticket;
    }

    /**
     * Retrieves all tickets from the repository.
     *
     * @return A list of all {@link Ticket} objects.
     */
    public List<Ticket> getAllTickets() {
        return new ArrayList<>(TicketRepository.tickets.values());
    }

    /**
     * Retrieves a ticket by its unique ID.
     *
     * @param id The unique ID of the ticket.
     * @return The {@link Ticket} object.
     * @throws CustomException if the ticket with the specified ID is not found.
     */
    public Ticket getTicketById(Integer id) {
        return getTicketOrThrow(id);
    }

    /**
     * Adds lines to an existing ticket.
     *
     * @param id        The unique ID of the ticket to which lines should be added.
     * @param lineCount Number of lines to add.
     * @return The updated {@link Ticket} object.
     * @throws CustomException if the ticket is not found or has already been checked.
     */
    public Ticket addLines(Integer id, int lineCount) {
        Ticket ticket = getTicketOrThrow(id);

        if (ticket.isChecked()) {
            throw new CustomException(
                    ErrorCode.NOT_MODIFIABLE,
                    "Ticket ID " + id + " cannot be modified as it has already been checked.",
                    HttpStatus.CONFLICT
            );
        }

        ticket.addLines(generateLines(lineCount));
        return ticket;
    }

    /**
     * Checks the status of a ticket and evaluates its lines.
     *
     * @param id The unique ID of the ticket to be checked.
     * @return A sorted list of {@link Line} objects with evaluated results.
     * @throws CustomException if the ticket is not found.
     */
    public Ticket checkTicketStatus(Integer id) {
        Ticket ticket = getTicketOrThrow(id);
        ticket.setChecked(true); // Mark the ticket as checked
        // Sort lines by their result in descending order
        ticket.setLines(ticket.getLines()
                .stream()
                .sorted(Comparator.comparingInt(Line::getResult).reversed())
                .toList());
        return ticket;
    }

    /**
     * Generates random lines for a ticket.
     *
     * @param count The number of lines to generate.
     * @return A list of {@link Line} objects.
     */
    private List<Line> generateLines(int count) {
        Random random = new Random();
        List<Line> lines = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            lines.add(new Line(random.nextInt(3), random.nextInt(3), random.nextInt(3)));
        }
        return lines;
    }

    /**
     * Retrieves a ticket from the repository or throws an exception if not found.
     *
     * @param id The unique ID of the ticket.
     * @return The {@link Ticket} object.
     * @throws CustomException if the ticket is not found.
     */
    private Ticket getTicketOrThrow(Integer id) {
        Ticket ticket = TicketRepository.tickets.get(id);
        if (ticket == null) {
            throw new CustomException(
                    ErrorCode.TICKET_NOT_FOUND,
                    "Ticket not found for ID: " + id,
                    HttpStatus.NOT_FOUND
            );
        }
        return ticket;
    }
}
