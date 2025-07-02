package com.example.test_task_gts.exception;

import com.example.test_task_gts.dto.StatusResponse;
import com.example.test_task_gts.enums.Status;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TableAlreadyExistsException.class)
    public ResponseEntity<StatusResponse<Object>> handleTableAlreadyExists(TableAlreadyExistsException ex) {
        StatusResponse<Object> statusResponse = StatusResponse.builder()
                .code(Status.TABLE_ALREADY_EXISTS.getCode())
                .message(Status.TABLE_ALREADY_EXISTS.getStatus())
                .data(ex.getMessage())
                .build();
        return new ResponseEntity<>(statusResponse, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(TableNotFoundException.class)
    public ResponseEntity<StatusResponse<Object>> handleTableNotFound(TableNotFoundException ex) {
        StatusResponse<Object> statusResponse = StatusResponse.builder()
                .code(Status.TABLE_NOT_FOUND.getCode())
                .message(Status.TABLE_NOT_FOUND.getStatus())
                .data(ex.getMessage())
                .build();
        return new ResponseEntity<>(statusResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StatusResponse<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        StatusResponse<Object> statusResponse = StatusResponse.builder()
                .code(400)
                .message("validation failed, error: " + ex.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(statusResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ColumnNotExistsException.class)
    public ResponseEntity<StatusResponse<Object>> handleColumnNotExists(ColumnNotExistsException ex) {
        StatusResponse<Object> statusResponse = StatusResponse.builder()
                .code(Status.COLUMN_NOT_EXISTS.getCode())
                .message(Status.COLUMN_NOT_EXISTS.getStatus())
                .data(ex.getMessage())
                .build();
        return new ResponseEntity<>(statusResponse, HttpStatus.NOT_FOUND);
    }

}
