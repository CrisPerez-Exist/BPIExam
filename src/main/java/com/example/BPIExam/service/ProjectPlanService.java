package com.example.BPIExam.service;

import com.example.BPIExam.model.ProjectPlanEntity;
import com.example.BPIExam.response.DeleteResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProjectPlanService {

    public List<ProjectPlanEntity> getAllProjectPlan();

    public ProjectPlanEntity getProjectPlan(int projectPlanId);

    public ProjectPlanEntity addProject(ProjectPlanEntity projectPlanEntity);

    public List<ProjectPlanEntity> calculateAllSchedule();

    public ProjectPlanEntity calculateSchedule(int projectPlanId);

    public ProjectPlanEntity updateProject(int projectPlanId, ProjectPlanEntity projectPlanEntity);

    public DeleteResponse deleteProject(int projectPlanId);

}

