package com.rahul.lotteryassignment.repository;

import com.rahul.lotteryassignment.dto.Ticket;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rahul Kumar, reader.rahul@gmail.com
 * Repository for storing tickets.
 */
@Repository
public class TicketRepository {

    /**
     * In-memory map to store tickets by ID
     */
    public static final Map<Integer, Ticket> tickets = new HashMap<>();
}
