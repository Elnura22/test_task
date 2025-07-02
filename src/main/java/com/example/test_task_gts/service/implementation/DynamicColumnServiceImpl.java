package com.example.test_task_gts.service.implementation;

import com.example.test_task_gts.dto.requests.ColumnDtoRequest;
import com.example.test_task_gts.model.DynamicColumn;
import com.example.test_task_gts.model.DynamicTable;
import com.example.test_task_gts.repository.DynamicColumnRepository;
import com.example.test_task_gts.service.DynamicColumnService;
import com.example.test_task_gts.util.HelperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DynamicColumnServiceImpl implements DynamicColumnService {

    private final HelperUtil helperUtil;
    private final DynamicColumnRepository dynamicColumnRepository;

    public List<DynamicColumn> saveDynamicColumns(List<ColumnDtoRequest> columns, DynamicTable dynamicTable) {
        DynamicColumn dynamicColumn;
        List<DynamicColumn> columnsList = new ArrayList<>();
        for (ColumnDtoRequest column : columns) {
            dynamicColumn = DynamicColumn.builder()
                    .tableDefinitionId(dynamicTable)
                    .columnName(column.getName())
                    .columnType(column.getType())
                    .postgresColumnType(helperUtil.convertTypeToPostgresType(column.getType()))
                    .isNullable(column.getIsNullable())
                    .isPrimaryKeyInternal(true)
                    .createdAt(LocalDateTime.now())
                    .build();
            dynamicColumnRepository.save(dynamicColumn);
            columnsList.add(dynamicColumn);
        }
        return columnsList;
    }

    @Override
    public List<DynamicColumn> getDynamicColumns(DynamicTable dynamicTable) {
        List<DynamicColumn> dynamicColumns = dynamicColumnRepository.findByTableDefinitionId(dynamicTable);
        if (dynamicColumns != null) {
            return dynamicColumns;
        }
        return List.of();
    }
}
