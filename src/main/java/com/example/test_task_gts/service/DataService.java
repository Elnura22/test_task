package com.example.test_task_gts.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface DataService {
    ResponseEntity<Map<String, Object>> createRecord(String tableName, Map<String, Object> recordData);
}
