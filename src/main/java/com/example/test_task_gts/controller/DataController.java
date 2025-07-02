package com.example.test_task_gts.controller;

import com.example.test_task_gts.service.DataService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/dynamic-tables/data/")
public class DataController {   // контроллер для работы с данными, crud 2.5

    private final DataService dataService;

    @PostMapping("/{tableName}")
    public ResponseEntity<Map<String, Object>> createRecord(@PathVariable("tableName") @NotBlank String tableName,
                                          @RequestBody Map<String, Object> recordData) {
        return dataService.createRecord(tableName, recordData);
    }
}
