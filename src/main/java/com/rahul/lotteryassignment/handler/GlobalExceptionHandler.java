package com.rahul.lotteryassignment.handler;

import com.rahul.lotteryassignment.constant.ErrorCode;
import com.rahul.lotteryassignment.dto.LotteryApiResponse;
import com.rahul.lotteryassignment.exception.CustomException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * @author Rahul Kumar, reader.rahul@gmail.com
 * Global exception handler across the entire lottery system API.
 * <p>
 * This class handles various exceptions and provides consistent error responses
 * to clients. It uses different HTTP status codes based on the type of exception
 * and formats the error messages accordingly.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles custom exceptions thrown within the lottery system.
     *
     * @param ex the CustomException instance thrown
     * @return a ResponseEntity with a BAD_REQUEST status and an LotteryApiResponse containing the error message
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<LotteryApiResponse<Object>> handleCustomException(CustomException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        return ResponseEntity.status(null != ex.getHttpStatus() ? ex.getHttpStatus() : HttpStatus.BAD_REQUEST)
                .body(LotteryApiResponse.error(
                        String.format("[%s] %s", errorCode.getCode(), errorCode.getMessage()),
                        null
                ));
    }

    /**
     * Handles validation constraint violations.
     *
     * @param e the ConstraintViolationException instance thrown
     * @return a ResponseEntity with a BAD_REQUEST status and an LotteryApiResponse containing the error message
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<LotteryApiResponse<Object>> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(LotteryApiResponse.error(String.format("[%s:%s] - %s",
                                ErrorCode.INVALID_INPUT.getCode(),
                                ErrorCode.INVALID_INPUT.getMessage(),
                                errorMessage),
                        null));
    }

    /**
     * Handles all other generic exceptions that are not explicitly handled.
     *
     * @param e the Exception instance thrown
     * @return a ResponseEntity with an INTERNAL_SERVER_ERROR status and an LotteryApiResponse containing the error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<LotteryApiResponse<Object>> handleGenericException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(LotteryApiResponse.error(e.getMessage(), null));
    }
}
