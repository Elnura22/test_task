package com.example.test_task_gts.repository;

import com.example.test_task_gts.model.DynamicTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamicTableRepository extends JpaRepository<DynamicTable, Long> {

    boolean existsByTableName(String tableName);
}
