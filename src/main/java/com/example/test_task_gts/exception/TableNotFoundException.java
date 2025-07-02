package com.example.test_task_gts.exception;

public class TableNotFoundException extends RuntimeException {
    public TableNotFoundException(String message) {
        super("table with this name " + message + " not found");
    }
}
