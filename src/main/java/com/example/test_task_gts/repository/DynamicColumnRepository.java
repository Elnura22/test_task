package com.example.test_task_gts.repository;

import com.example.test_task_gts.model.DynamicColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamicColumnRepository extends JpaRepository<DynamicColumn, Long> {
}
