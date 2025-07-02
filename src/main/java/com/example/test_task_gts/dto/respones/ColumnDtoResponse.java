package com.example.test_task_gts.dto.respones;

import com.example.test_task_gts.enums.ColumnType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnDtoResponse {
    private String name;
    private ColumnType type;
    private String postgresType;
    private boolean isNullable;
    private boolean isPrimaryKey;
}
