package com.example.test_task_gts.controller;

import com.example.test_task_gts.dto.requests.CreateSchemaRequest;
import com.example.test_task_gts.dto.respones.CreateSchemaResponse;
import com.example.test_task_gts.service.DynamicTableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dynamic-tables")
public class DynamicTableController {

    private final DynamicTableService dynamicTableService;

    @PostMapping("/schemas")
    public ResponseEntity<CreateSchemaResponse> createSchema(@RequestBody @Valid CreateSchemaRequest request) {
        return dynamicTableService.createSchema(request);
    }
}
