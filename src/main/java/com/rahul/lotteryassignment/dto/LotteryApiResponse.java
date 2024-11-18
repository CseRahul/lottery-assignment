package com.rahul.lotteryassignment.dto;

import lombok.Getter;

import java.time.Instant;

/**
 * @param <T> The type of the response data
 * @author Rahul Kumar, reader.rahul@gmail.com
 * Generic class for representing API responses.
 */
@Getter
public class LotteryApiResponse<T> {

    private final boolean success;//Represents success or failures of response
    private final String message; //Details of Response or Error Message
    private final T data; // Data associated with Response
    private final Instant timestamp; //Response Generation timestamp

    /**
     * Constructor for creating an LotteryApiResponse.
     *
     * @param success Indicates success or failure
     * @param message Response message
     * @param data    Response data
     */
    public LotteryApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = Instant.now(); // Automatically adds a timestamp
    }

    /**
     * Factory method to create a successful response.
     *
     * @param message Success message
     * @param data    Response data
     * @param <T>     Type of data
     * @return LotteryApiResponse instance
     */
    public static <T> LotteryApiResponse<T> success(String message, T data) {
        return new LotteryApiResponse<>(true, message, data);
    }

    /**
     * Factory method to create an error response.
     *
     * @param message Error message
     * @param data    Error-related data
     * @param <T>     Type of data
     * @return LotteryApiResponse instance
     */
    public static <T> LotteryApiResponse<T> error(String message, T data) {
        return new LotteryApiResponse<>(false, message, data);
    }
}
