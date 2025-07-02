package com.example.test_task_gts.controller;

import com.example.test_task_gts.dto.requests.CreateSchemaRequest;
import com.example.test_task_gts.dto.respones.CreateSchemaResponse;
import com.example.test_task_gts.dto.respones.TableDto;
import com.example.test_task_gts.service.DynamicTableService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/dynamic-tables")
public class DynamicTableController {

    private final DynamicTableService dynamicTableService;

    @PostMapping("/schemas")
    public ResponseEntity<CreateSchemaResponse> createSchema(@RequestBody @Valid CreateSchemaRequest request) {
        return dynamicTableService.createSchema(request);
    }

    @GetMapping("/schemas/{tableName}")
    public ResponseEntity<CreateSchemaResponse> getSchema(@PathVariable @NotBlank String tableName) {
        return dynamicTableService.getSchema(tableName);
    }

    @GetMapping("/schemas")
    public ResponseEntity<List<TableDto>> getAllTablesInfo() {
        return dynamicTableService.getAllTablesInfo();
    }

}
