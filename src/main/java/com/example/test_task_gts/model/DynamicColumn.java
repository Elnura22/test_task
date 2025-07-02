package com.example.test_task_gts.model;

import com.example.test_task_gts.enums.ColumnType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "app_dynamic_column_definition")
public class DynamicColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_definition_id", nullable = false)
    private DynamicTable tableDefinitionId;

    @Column(nullable = false, length = 255)
    private String columnName;

    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private ColumnType columnType;

    @Column(nullable = false, length = 100)
    private String postgresColumnType;

    @Column(nullable = false)
    private Boolean isNullable;

    @Column(nullable = false)
    private Boolean isPrimaryKeyInternal;

    @Column(nullable = false)
    private LocalDateTime createdAt;

}
