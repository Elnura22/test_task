package com.example.test_task_gts.service.implementation;

import com.example.test_task_gts.dto.requests.ColumnDtoRequest;
import com.example.test_task_gts.dto.requests.CreateSchemaRequest;
import com.example.test_task_gts.dto.respones.ColumnDtoResponse;
import com.example.test_task_gts.dto.respones.CreateSchemaResponse;
import com.example.test_task_gts.dto.respones.TableDto;
import com.example.test_task_gts.exception.TableAlreadyExistsException;
import com.example.test_task_gts.exception.TableNotFoundException;
import com.example.test_task_gts.model.DynamicColumn;
import com.example.test_task_gts.model.DynamicTable;
import com.example.test_task_gts.repository.DynamicTableRepository;
import com.example.test_task_gts.service.DynamicColumnService;
import com.example.test_task_gts.service.DynamicTableService;
import com.example.test_task_gts.util.HelperUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class DynamicTableServiceImpl implements DynamicTableService {

    private final JdbcTemplate jdbcTemplate;
    private final DynamicTableRepository dynamicTableRepository;
    private final DynamicColumnService dynamicColumnService;
    private final HelperUtil helperUtil;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<CreateSchemaResponse> createSchema(CreateSchemaRequest request) {
        validateTableExists(request.getTableName());
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
                .append(helperUtil.quoteIdentifier(request.getTableName()))
                .append(" (id BIGSERIAL PRIMARY KEY,");
        List<ColumnDtoRequest> columns = request.getColumns();
        for (ColumnDtoRequest column : columns) {
            script.append(helperUtil.quoteIdentifier(column.getName())).append(" ")
                    .append(helperUtil.convertTypeToPostgresType(column.getType())).append(" ");
            script.append("NOT NULL ");
            script.append(",");
        }
        script.deleteCharAt(script.length() - 1);
        script.append(");");
        jdbcTemplate.execute(script.toString());
        log.info("table {} created", request.getTableName());
    }


    private CreateSchemaResponse createSchemaResponse(DynamicTable dynamicTable, List<ColumnDtoResponse> columns) {
        return CreateSchemaResponse.builder()
                .tableName(dynamicTable.getTableName())
                .userFriendlyName(dynamicTable.getUserFriendlyName())
                .columns(columns)
                .build();
    }

    @Override
    public ResponseEntity<CreateSchemaResponse> getSchema(String tableName) {  // два запроса в базу, нужно потом через @Query вытащить
        Optional<DynamicTable> dynamicTable = dynamicTableRepository.findByTableName(tableName);
        if (dynamicTable.isEmpty()) {
            throw new TableNotFoundException(tableName);
        }
        List<DynamicColumn> listColumns = dynamicColumnService.getDynamicColumns(dynamicTable.get());
        CreateSchemaResponse response = CreateSchemaResponse.builder()
                .tableName(dynamicTable.get().getTableName())
                .userFriendlyName(dynamicTable.get().getUserFriendlyName())
                .columns(columnsToColumnDtoResponse(listColumns))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void validateTableExists(String tableName) {
        boolean exists = dynamicTableRepository.existsByTableName(tableName);
        if (exists) {
            throw new TableAlreadyExistsException(tableName);
        }
    }

    @Override
    public ResponseEntity<List<TableDto>> getAllTablesInfo() {
        List<TableDto> listInfoTables = new ArrayList<>();
        List<Object[]> testList = dynamicTableRepository.findAllWithColumnCount();
        for (Object[] value : testList) { //не смогла сразу TableDto вытащить
            String tableName = (String) value[0];
            String userFriendlyName = (String) value[1];
            int columnCount = (((Long) value[2]).intValue());
            TableDto tableDto = TableDto.builder()
                    .tableName(tableName)
                    .userFriendlyName(userFriendlyName)
                    .columnCount(columnCount)
                    .build();
            listInfoTables.add(tableDto);
        }
        listInfoTables.sort(Comparator.comparing(TableDto::getTableName));
        return new ResponseEntity<>(listInfoTables, HttpStatus.OK);
    }
}
