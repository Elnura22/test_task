package com.example.test_task_gts.util;

import com.example.test_task_gts.enums.ColumnType;
import org.springframework.stereotype.Component;

@Component
public class HelperUtil {

    public String convertTypeToPostgresType(ColumnType columnType) {
        return switch (columnType) {
            case TEXT -> "TEXT";
            case INTEGER -> "INTEGER";
            case BIGINT -> "BIGINT";
            case DECIMAL -> "NUMERIC(19,4)";
            case BOOLEAN -> "BOOLEAN";
            case DATE -> "DATE";
            case TIMESTAMP -> "TIMESTAMP WITHOUT TIME ZONE";
        };
    }


    public String quoteIdentifier(String identifier) {
        return "\"" + identifier.replace("\"", "\"\"") + "\"";
    }

}

// здесь можно было конвертнуть не в стринг, а в PostgresType enum