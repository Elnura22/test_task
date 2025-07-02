package com.example.test_task_gts.dto.respones;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableDto {
    private String tableName;
    private String userFriendlyName;
    private int columnCount;
}
