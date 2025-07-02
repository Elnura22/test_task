package com.example.test_task_gts.service.implementation;

import com.example.test_task_gts.dto.requests.ColumnDtoRequest;
import com.example.test_task_gts.dto.requests.CreateSchemaRequest;
import com.example.test_task_gts.dto.respones.ColumnDtoResponse;
import com.example.test_task_gts.dto.respones.CreateSchemaResponse;
import com.example.test_task_gts.exception.TableAlreadyExistsException;
import com.example.test_task_gts.model.DynamicColumn;
import com.example.test_task_gts.model.DynamicTable;
import com.example.test_task_gts.repository.DynamicTableRepository;
import com.example.test_task_gts.service.DynamicColumnService;
import com.example.test_task_gts.service.DynamicTableService;
import com.example.test_task_gts.util.TypeConverter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class DynamicTableServiceImpl implements DynamicTableService {

    private final JdbcTemplate jdbcTemplate;
    private final DynamicTableRepository dynamicTableRepository;
    private final DynamicColumnService dynamicColumnService;
    private final TypeConverter typeConverter;

    @Override
    @Transactional
    public ResponseEntity<CreateSchemaResponse> createSchema(CreateSchemaRequest request) {
        boolean exists = dynamicTableRepository.existsByTableName(request.getTableName());
        if (exists) {
            throw new TableAlreadyExistsException(request.getTableName());
        }
        log.info("list columns{}", request.getColumns());
        createSqlTable(request);
        DynamicTable dynamicTable = saveDynamicTable(request);
        List<DynamicColumn> savedDynamicColumnList = dynamicColumnService.saveDynamicColumns(request.getColumns(), dynamicTable);
        List<ColumnDtoResponse> columnsResponseList = columnsToColumnDtoResponse(savedDynamicColumnList);
        CreateSchemaResponse response = createSchemaResponse(dynamicTable, columnsResponseList);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private List<ColumnDtoResponse> columnsToColumnDtoResponse(List<DynamicColumn> columns) {
        return columns.stream().map(column ->
                new ColumnDtoResponse(
                        column.getColumnName(),
                        column.getColumnType(),
                        column.getPostgresColumnType(),
                        column.getIsNullable(),
                        column.getIsPrimaryKeyInternal()
                )
        ).toList();
    }

    private DynamicTable saveDynamicTable(CreateSchemaRequest request) {
        DynamicTable dynamicTable = DynamicTable.builder()
                .tableName(request.getTableName())
                .userFriendlyName(request.getUserFriendlyName())
                .createdAt(LocalDateTime.now())
                .build();
        return dynamicTableRepository.save(dynamicTable);
    }

    private void createSqlTable(CreateSchemaRequest request) {
        StringBuilder script = new StringBuilder();
        script.append("CREATE TABLE ")
                .append(quoteIdentifier(request.getTableName()))
                .append(" (id BIGSERIAL PRIMARY KEY,");
        List<ColumnDtoRequest> columns = request.getColumns();
        for (ColumnDtoRequest column : columns) {
            script.append(quoteIdentifier(column.getName())).append(" ")
                    .append(typeConverter.convertTypeToPostgresType(column.getType())).append(" ");
            script.append("NOT NULL ");
            script.append(",");
        }
        script.deleteCharAt(script.length() - 1);
        script.append(");");
        jdbcTemplate.execute(script.toString());
        log.info("table {} created", request.getTableName());
    }

    private String quoteIdentifier(String identifier) {
        return "\"" + identifier.replace("\"", "\"\"") + "\"";
    }

    private CreateSchemaResponse createSchemaResponse(DynamicTable dynamicTable, List<ColumnDtoResponse> columns) {
        return CreateSchemaResponse.builder()
                .tableName(dynamicTable.getTableName())
                .userFriendlyName(dynamicTable.getUserFriendlyName())
                .columns(columns)
                .build();
    }
}
