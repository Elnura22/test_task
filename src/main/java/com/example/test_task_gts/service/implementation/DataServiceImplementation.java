package com.example.test_task_gts.service.implementation;

import com.example.test_task_gts.exception.ColumnNotExistsException;
import com.example.test_task_gts.model.DynamicColumn;
import com.example.test_task_gts.model.DynamicTable;
import com.example.test_task_gts.repository.DynamicColumnRepository;
import com.example.test_task_gts.repository.DynamicTableRepository;
import com.example.test_task_gts.service.DataService;
import com.example.test_task_gts.util.HelperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class DataServiceImplementation implements DataService {

    private final DynamicTableRepository dynamicTableRepository;
    private final DynamicColumnRepository dynamicColumnRepository;
    private final HelperUtil helperUtil;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public ResponseEntity<Map<String, Object>> createRecord(String tableName, Map<String, Object> recordData) {
        Optional<DynamicTable> dynamicTable = dynamicTableRepository.findByTableName(tableName);
        Map<String, Object> response = new HashMap<>();
        if (dynamicTable.isPresent()) {
            List<DynamicColumn> columns = dynamicColumnRepository.findByTableDefinitionId(dynamicTable.get()); // get schema columns
            Set<String> existColumnsOfTable = columns.stream().map(DynamicColumn::getColumnName).collect(Collectors.toSet()); // get existing columns name
            for (DynamicColumn column : columns) { // проверка isNullable==false
                if (!recordData.containsKey("isNullable") && !column.getIsNullable()) {
                    throw new ColumnNotExistsException("column " + column.getColumnName() + "required");
                }
            }
            //конвертация типов, если они не совпадают, то будет ошибка  - не успела сделать(можно switchCase)
            //создание записи через jdbcTemplate
            List<Object> values = new ArrayList<>();
            StringBuilder script = new StringBuilder("INSERT INTO ");
            script.append(helperUtil.quoteIdentifier(tableName)).append(" (");
            for (String columnName : existColumnsOfTable) {
                if (recordData.containsKey(columnName)) { //совпадают ли колонки существующие с теми которые пришли
                    script.append(helperUtil.quoteIdentifier(columnName)).append(", ");  //добавляем к скрипту
                    values.add(recordData.get(columnName)); //добавили значения которые пришли
                    response.put(columnName, recordData.get(columnName)); //put values for response  //айдишка не положилась - запрос в базу за ней?
                } else {
                    throw new ColumnNotExistsException("column doesnt exist");
                }
            }
            script.setLength(script.length() - 2);
            script.append(") VALUES (");
            script.append(values.stream().map(value -> "?").collect(Collectors.joining(", ")));
            script.append(")");
            log.info("script {}", script);
            log.info(values.toString());
            jdbcTemplate.update(script.toString(), values.toArray());
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
