package com.example.BPIExam.repository;

import com.example.BPIExam.model.ProjectPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectPlanEntityRepository extends JpaRepository<ProjectPlanEntity, Integer> {
}
