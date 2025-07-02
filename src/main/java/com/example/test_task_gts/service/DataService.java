package com.example.test_task_gts.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface DataService {
    ResponseEntity<Map<String, Object>> createRecord(String tableName, Map<String, Object> recordData);

    ResponseEntity<Object> deleteRecord(String tableName, @NotNull Long id);

    ResponseEntity<Map<String, Object>> getRecord(@NotBlank String tableName, @NotNull Long id);
}
