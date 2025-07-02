package com.example.test_task_gts.exception;

import com.example.test_task_gts.dto.StatusResponse;
import com.example.test_task_gts.enums.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(TableAlreadyExistsException.class)
    public ResponseEntity<StatusResponse<Object>> handleTableAlreadyExists(TableAlreadyExistsException ex) {
        StatusResponse<Object> statusResponse = StatusResponse.builder()
                .code(Status.TABLE_ALREADY_EXISTS.getCode())
                .message(Status.TABLE_ALREADY_EXISTS.getStatus())
                .data(ex.getMessage())
                .build();
        return new ResponseEntity<>(statusResponse, HttpStatus.CONFLICT);
    }
}
