package com.example.test_task_gts.repository;

import com.example.test_task_gts.model.DynamicTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DynamicTableRepository extends JpaRepository<DynamicTable, Long> {

    boolean existsByTableName(String tableName);

    Optional<DynamicTable> findByTableName(String tableName);

    @Query("""
              SELECT t.tableName AS tableName,
                         t.userFriendlyName AS userFriendlyName,
                         COUNT(c.id) AS columnCount
                  FROM DynamicTable t
                  LEFT JOIN DynamicColumn c ON c.tableDefinitionId = t
                  GROUP BY t.id
            """)
    List<Object[]> findAllWithColumnCount();
}
