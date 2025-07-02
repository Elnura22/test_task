package com.example.test_task_gts.dto.requests;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSchemaRequest {
    @NotBlank
    @Size(min = 3, max = 63, message = "tableName should be between 3 and 63 characters")
    @Pattern(
            regexp = "^(?!pg_)(?!app_)[a-z0-9_]+$",
            message = "tableName should contain characters, numbers  and symbol (_). and dont start with pg_ or app_"
    )
    private String tableName;
    @Nullable
    private String userFriendlyName;
    @NotEmpty
    private List<ColumnDtoRequest> columns;
}
