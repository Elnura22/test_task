package com.example.test_task_gts.exception;

public class TableAlreadyExistsException extends RuntimeException {
    public TableAlreadyExistsException(String message) {
        super("table with this name " + message + " already exists");
    }
}
