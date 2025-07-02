package com.example.test_task_gts.controller;

import com.example.test_task_gts.service.DataService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/dynamic-tables/data")
public class DataController {   // контроллер для работы с данными, crud 2.5

    private final DataService dataService;

    @PostMapping("/{tableName}")
    public ResponseEntity<Map<String, Object>> createRecord(@PathVariable("tableName") @NotBlank String tableName,
                                          @RequestBody Map<String, Object> recordData) {
        return dataService.createRecord(tableName, recordData);
    }
    //2.5.2. Чтение Списка Записей (с пагинацией)
    // Эндпоинт: GET /api/v1/dynamic-tables/data/{tableName}

    //2.5.3. Чтение Записи по ID
    //Эндпоинт: GET /api/v1/dynamic-tables/data/{tableName}/{id}

    //2.5.4. Обновление Записи (Полное)
    //Эндпоинт: PUT /api/v1/dynamic-tables/data/{tableName}/{id}

    @DeleteMapping("/{tableName}/{id}")
    public ResponseEntity<Object> deleteRecord(@PathVariable String tableName,
                                               @PathVariable("id") @NotNull Long id) {
        return dataService.deleteRecord(tableName, id);
    }
}
