package com.example.test_task_gts.service;

import com.example.test_task_gts.dto.requests.CreateSchemaRequest;
import com.example.test_task_gts.dto.respones.CreateSchemaResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface DynamicTableService {
    ResponseEntity<CreateSchemaResponse> createSchema(@Valid CreateSchemaRequest request);
}
