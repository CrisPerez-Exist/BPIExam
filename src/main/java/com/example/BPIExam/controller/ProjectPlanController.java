package com.example.BPIExam.controller;

import com.example.BPIExam.model.ProjectPlanEntity;
import com.example.BPIExam.response.DeleteResponse;
import com.example.BPIExam.service.ProjectPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectPlanController {

    @Autowired
    ProjectPlanService projectPlanService;

    @GetMapping("/project-plan/")
    public List<ProjectPlanEntity> getAllProjects(
    ) {
        return projectPlanService.getAllProjectPlan();
    }

    @GetMapping("/project-plan/{projectPlanId}")
    public ProjectPlanEntity getProject(
            @PathVariable int projectPlanId
    ) {
        return projectPlanService.getProjectPlan(projectPlanId);
    }

    @PostMapping("/project-plan/")
    public ProjectPlanEntity addProject(
            @RequestBody ProjectPlanEntity projectPlanEntity
    ) {
        return projectPlanService.addProject(projectPlanEntity);
    }

    @GetMapping("/project-plan/calculate-schedule/")
    public List<ProjectPlanEntity> calculateAllSchedule() {
        return projectPlanService.calculateAllSchedule();
    }

    @GetMapping("/project-plan/calculate-schedule/{id}")
    public ProjectPlanEntity calculateSchedule(
            @PathVariable int id
    ) {
        return projectPlanService.calculateSchedule(id);
    }

    @PatchMapping ("/project-plan/{id}")
    public ProjectPlanEntity addProject(
            @PathVariable int id,
            @RequestBody ProjectPlanEntity projectPlanEntity
    ) {
        return projectPlanService.updateProject(id, projectPlanEntity);
    }

    @DeleteMapping ("/project-plan/{id}")
    public DeleteResponse deleteProject(
            @PathVariable int id
    ) {
        return projectPlanService.deleteProject(id);
    }

}