package com.ead.authuser.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public final class ExceptionHandler {

    public ResponseEntity<Object> handleNotFoundHttpException(final Exception e) {
        final var message = new ExceptionJsonMessage(NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(message, NOT_FOUND);
    }

    private record ExceptionJsonMessage(
            Integer status,
            @JsonProperty("message") String payload,
            @CreatedDate
            LocalDateTime timestamp
    ) {
        public ExceptionJsonMessage(final Integer status, final String payload) {
            this(status, payload, LocalDateTime.now());
        }
    }
}
