package com.example.test_task_gts.dto.requests;

import com.example.test_task_gts.enums.ColumnType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnDtoRequest {
    @NotBlank
    @Size(min = 3, max = 63, message = "name should be between 3 and 63 characters")
    @Pattern(
            regexp = "^(?!pg_)(?!app_)[a-z0-9_]+$",
            message = "name should contain characters, numbers  and symbol (_). and dont start with pg_ or app_"
    )
    private String name;
    @NotNull
    private ColumnType type;
    @NotNull
    private Boolean isNullable;
}
