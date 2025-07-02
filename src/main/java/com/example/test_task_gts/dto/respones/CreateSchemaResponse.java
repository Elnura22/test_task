package com.example.test_task_gts.dto.respones;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSchemaResponse {

    private String tableName;
    private String userFriendlyName;
    private List<ColumnDtoResponse> columns;

}
