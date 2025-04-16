package com.example.BPIExam.repository;

import com.example.BPIExam.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskEntityRepository extends JpaRepository<TaskEntity, Integer> {

    @Query(value = "SELECT * FROM TASK_ENTITY task INNER JOIN TASK_DEPENDENCY dependency " +
            "ON task.ID = dependency.TASK_ID " +
            "WHERE dependency.TASK_DEPENDENCY_ID = :taskId", nativeQuery = true)
    List<TaskEntity> findTaskDependent(@Param("taskId") int taskId);
}
