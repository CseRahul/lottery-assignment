package com.rahul.lotteryassignment.exception;

import com.rahul.lotteryassignment.constant.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Rahul Kumar, reader.rahul@gmail.com
 * <p>
 * Custom exception class used to represent specific errors in the lottery system.
 * </p>
 */
@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode; //he error code associated with this exception.
    private final HttpStatus httpStatus; //The HTTP status associated with this exception.

    /**
     * Constructs a new CustomException with the given error code.
     * The HTTP status is set to {@link HttpStatus#INTERNAL_SERVER_ERROR} by default,
     * can be overridden by caller
     *
     * @param errorCode the error code describing the type of error
     */
    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * Constructs a new CustomException with a custom HTTP status.
     * The error code is set to {@link ErrorCode#INTERNAL_SERVER_ERROR} by default.
     *
     * @param httpStatus the HTTP status to be associated with the exception
     */
    public CustomException(HttpStatus httpStatus) {
        super(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
        this.errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        this.httpStatus = httpStatus;
    }

    /**
     * Constructs a new CustomException with the given error code, custom message, and HTTP status.
     *
     * @param errorCode     the error code describing the type of error
     * @param customMessage the custom message for this exception
     * @param httpStatus    the HTTP status to be associated with the exception
     */
    public CustomException(ErrorCode errorCode, String customMessage, HttpStatus httpStatus) {
        super(customMessage);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    /**
     * @return the string representation of the error code
     */
    public String getErrorCodeValue() {
        return errorCode.getCode();
    }
}
