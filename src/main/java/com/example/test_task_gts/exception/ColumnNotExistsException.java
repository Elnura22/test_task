package com.example.test_task_gts.exception;

public class ColumnNotExistsException extends RuntimeException {
    public ColumnNotExistsException(String message) {
        super("column not exists: " + message);
    }
}
