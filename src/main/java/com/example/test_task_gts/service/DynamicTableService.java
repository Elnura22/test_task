package com.example.test_task_gts.service;

import com.example.test_task_gts.dto.requests.CreateSchemaRequest;
import com.example.test_task_gts.dto.respones.CreateSchemaResponse;
import com.example.test_task_gts.dto.respones.TableDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DynamicTableService {
    ResponseEntity<CreateSchemaResponse> createSchema(@Valid CreateSchemaRequest request);

    ResponseEntity<CreateSchemaResponse> getSchema(String tableName);

    ResponseEntity<List<TableDto>> getAllTablesInfo();
}
