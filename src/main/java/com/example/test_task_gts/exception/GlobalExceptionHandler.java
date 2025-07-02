package com.example.test_task_gts.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TableAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleTableAlreadyExists(
            TableAlreadyExistsException ex, HttpServletRequest request) {
        return bodyResponse(HttpStatus.CONFLICT, "conflict", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(TableNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTableNotFound(
            TableNotFoundException ex, HttpServletRequest request) {
        return bodyResponse(HttpStatus.NOT_FOUND, "not found", ex.getMessage(), request.getRequestURI());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(
            ConstraintViolationException ex, HttpServletRequest request) {
        return bodyResponse(HttpStatus.BAD_REQUEST, "bad request", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(ColumnNotExistsException.class)
    public ResponseEntity<Map<String, Object>> handleColumnNotExists(
            ColumnNotExistsException ex, HttpServletRequest request) {
        return bodyResponse(HttpStatus.NOT_FOUND, "not found", ex.getMessage(), request.getRequestURI());
    }


    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleRecordNotFound(
            RecordNotFoundException ex, HttpServletRequest request) {
        return bodyResponse(HttpStatus.NOT_FOUND, "not found", ex.getMessage(), request.getRequestURI());
    }

    private ResponseEntity<Map<String, Object>> bodyResponse(HttpStatus status, String error, String message, String path) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        body.put("path", path);
        return new ResponseEntity<>(body, status);
    }

}
