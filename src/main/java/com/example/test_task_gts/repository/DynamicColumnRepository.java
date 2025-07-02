package com.example.test_task_gts.repository;

import com.example.test_task_gts.model.DynamicColumn;
import com.example.test_task_gts.model.DynamicTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DynamicColumnRepository extends JpaRepository<DynamicColumn, Long> {

    List<DynamicColumn> findByTableDefinitionId(DynamicTable dynamicTable);
}
