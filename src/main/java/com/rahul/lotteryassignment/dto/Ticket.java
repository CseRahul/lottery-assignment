package com.rahul.lotteryassignment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Rahul Kumar, reader.rahul@gmail.com
 * Represents a Ticket in the lottery system.
 * Contains a unique ID, a list of Lines, and a flag to check if the ticket has been evaluated.
 */
@Getter
@Setter
public class Ticket {
    @NotNull
    private Integer id; //Unique ID of the ticket
    @NotNull
    private List<Line> lines; //List of Lines on the ticket
    private boolean checked; //Indicates whether the ticket status has been checked

    /**
     * Default constructor. Generates a random ID for the ticket and initializes the lines list.
     */
    public Ticket() {
        this.id = ThreadLocalRandom.current().nextInt(1000, 9999 + 1);
        this.lines = new ArrayList<>();
        this.checked = false;
    }

    /**
     * Adds new lines to the ticket.
     *
     * @param newLines List of lines to be added
     */
    public void addLines(List<Line> newLines) {
        this.lines.addAll(newLines);
    }
}
