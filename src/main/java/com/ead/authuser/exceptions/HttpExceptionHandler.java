package com.ead.authuser.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestControllerAdvice
public final class HttpExceptionHandler {

    @ExceptionHandler(NotFoundHttpException.class)
    public ResponseEntity<Object> handleNotFoundHttpException(final Exception e) {
        final var message = new ExceptionJsonMessage(NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(message, NOT_FOUND);
    }

    @ExceptionHandler(BadRequestHttpException.class)
    public ResponseEntity<Object> handleBadRequestHttpException(final Exception e) {
        final var message = new ExceptionJsonMessage(BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(message, BAD_REQUEST);
    }

    @ExceptionHandler(ConflictHttpException.class)
    public ResponseEntity<Object> handleConflictHttpException(final Exception e) {
        final var message = new ExceptionJsonMessage(CONFLICT.value(), e.getMessage());
        return new ResponseEntity<>(message, CONFLICT);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSQLException(final SQLException e) {
        var status = UNPROCESSABLE_ENTITY;
        final var duplicatedData = "already exists";

        if(e.getMessage().contains(duplicatedData)) { status = CONFLICT; }

        final var message = new ExceptionJsonMessage(status.value(), e.getMessage());
        return new ResponseEntity<>(message, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Collection<Object>> handleConstraintViolationException(final MethodArgumentNotValidException e) {
        final Set<ExceptionJsonMessage> violations = new HashSet<>();

        for(final var error : e.getBindingResult().getFieldErrors()) {
            final String payloadMessage = error.getField() + " " + error.getDefaultMessage();
            violations.add(new ExceptionJsonMessage(BAD_REQUEST.value(), payloadMessage));
        }

        return new ResponseEntity<>(Collections.singleton(violations), BAD_REQUEST);
    }

    private record ExceptionJsonMessage(
            Integer status,
            @JsonProperty("message") String payload,
            @CreatedDate
            @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
            LocalDateTime timestamp
    ) {
        public ExceptionJsonMessage(final Integer status, final String payload) {
            this(status, payload, LocalDateTime.now());
        }
    }
}
