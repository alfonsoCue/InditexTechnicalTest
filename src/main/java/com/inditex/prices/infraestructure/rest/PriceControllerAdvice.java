package com.inditex.prices.infraestructure.rest;

import com.inditex.prices.domain.exception.PriceNotFoundException;
import com.inditex.prices.infrastructure.model.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice(assignableTypes = PriceController.class)
public class PriceControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handle(ConstraintViolationException exception)
    {
        return buildResponse(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(PriceNotFoundException exception)
    {
        return buildResponse(HttpStatus.NOT_FOUND, exception);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception exception)
    {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus httpStatus, Exception exception)
    {
        ErrorResponse error = new ErrorResponse();
        error.setException(exception.getClass().getSimpleName());
        error.setMessage(exception.getMessage());
        error.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(httpStatus).body(error);
    }
}
