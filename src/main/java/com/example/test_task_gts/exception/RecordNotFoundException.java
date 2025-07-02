package com.example.test_task_gts.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String message) {
        super("record not found: " + message);
    }
}
