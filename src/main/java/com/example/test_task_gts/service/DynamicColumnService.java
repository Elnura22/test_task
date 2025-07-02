package com.example.test_task_gts.service;

import com.example.test_task_gts.dto.requests.ColumnDtoRequest;
import com.example.test_task_gts.model.DynamicColumn;
import com.example.test_task_gts.model.DynamicTable;

import java.util.List;

public interface DynamicColumnService {
    List<DynamicColumn> saveDynamicColumns(List<ColumnDtoRequest> columns, DynamicTable dynamicTable);

    List<DynamicColumn> getDynamicColumns(DynamicTable dynamicTable);
}
