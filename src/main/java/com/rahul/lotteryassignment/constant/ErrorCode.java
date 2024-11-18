package com.rahul.lotteryassignment.constant;

import lombok.Getter;

/**
 * @author Rahul Kumar, reader.rahul@gmail.com
 * <p>
 * Enums for maintaining Application Specific Error Codes and Satandard Messages
 */
@Getter
public enum ErrorCode {
    /**
     * Enums defining Custom ErrorCode and standard error message.
     */
    TICKET_NOT_FOUND("ERR-001", "Ticket Not Found"),
    INVALID_INPUT("ERR-002", "Invalid Input"),
    NOT_MODIFIABLE("ERR-003", "Not Modifiable"),
    TICKET_NOT_CREATED("ERR-004", "Ticket Not Created"),
    INTERNAL_SERVER_ERROR("ERR-500", "Unexpected Error");

    private final String code;
    private final String message;

    /**
     * Constructs an ErrorCode enum with the specified code and message.
     *
     * @param code    the unique error code
     * @param message the message describing the error
     */
    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
