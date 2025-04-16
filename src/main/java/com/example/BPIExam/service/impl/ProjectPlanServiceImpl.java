package com.example.BPIExam.service.impl;

import com.example.BPIExam.constants.Constant;
import com.example.BPIExam.helper.CalculateSchedule;
import com.example.BPIExam.helper.SetMessage;
import com.example.BPIExam.model.ProjectPlanEntity;
import com.example.BPIExam.model.TaskEntity;
import com.example.BPIExam.repository.ProjectPlanEntityRepository;
import com.example.BPIExam.repository.TaskEntityRepository;
import com.example.BPIExam.response.DeleteResponse;
import com.example.BPIExam.service.ProjectPlanService;
import com.example.BPIExam.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProjectPlanServiceImpl implements ProjectPlanService {

    @Autowired
    ProjectPlanEntityRepository projectPlanRepo;

    @Autowired
    CalculateSchedule calculateSchedule;

    @Autowired
    TaskService taskService;

    @Autowired
    SetMessage message;

    @Override
    public ProjectPlanEntity getProjectPlan(int projectPlanId) {

        return projectPlanRepo.findById(projectPlanId).orElseThrow(
                () -> new RuntimeException(Constant.PROJECT_PLAN_DOES_NOT_EXISTS));

    }

    @Override
    public List<ProjectPlanEntity> getAllProjectPlan() {
        return projectPlanRepo.findAll();
    }

    @Override
    @Transactional
    public ProjectPlanEntity addProject(ProjectPlanEntity projectPlanEntity) {

        projectPlanRepo.save(projectPlanEntity);

        if (!Objects.isNull(projectPlanEntity.getTasks())) {
            List<TaskEntity> taskInProject = new ArrayList<>();
            for (TaskEntity task : projectPlanEntity.getTasks()) {
                task = taskService.getTask(task.getId());
                taskInProject.add(task);
                task.setProjectPlanEntity(projectPlanEntity);
                taskService.updateTask(task.getId(), task);
            }
            projectPlanEntity.setTasks(taskInProject);
        }

        return projectPlanRepo.save(projectPlanEntity);
    }

    @Override
    @Transactional
    public List<ProjectPlanEntity> calculateAllSchedule() {
        List<ProjectPlanEntity> projectPlans = getAllProjectPlan();

        for (ProjectPlanEntity projectPlan : projectPlans) {
            projectPlanRepo.save(calculateSchedule.calculateSchedule(projectPlan));
        }

        return projectPlans;
    }

    @Override
    @Transactional
    public ProjectPlanEntity calculateSchedule(int projectPlanId) {

        Optional<ProjectPlanEntity> projectPlanEntity = projectPlanRepo.findById(projectPlanId);

        if (!Objects.isNull(projectPlanEntity)) {
            projectPlanRepo.save(calculateSchedule.calculateSchedule(projectPlanEntity.orElse(null)));
        }

        return projectPlanEntity.orElse(null);
    }

    @Override
    public ProjectPlanEntity updateProject(int projectPlanId, ProjectPlanEntity projectPlanEntity) {
        ProjectPlanEntity projectPlanEntityUpdate = projectPlanRepo.findById(projectPlanId)
                .orElseThrow(() -> new RuntimeException(Constant.PROJECT_PLAN_DOES_NOT_EXISTS));

        if (!Objects.isNull(projectPlanEntity.getProjectName())) {
            projectPlanEntityUpdate.setProjectName(projectPlanEntity.getProjectName());
        }

        if (!Objects.isNull(projectPlanEntity.getProjectDescription())) {
            projectPlanEntityUpdate.setProjectDescription(projectPlanEntity.getProjectDescription());
        }

        if (!Objects.isNull(projectPlanEntity.getTasks())) {
            projectPlanEntityUpdate.setTasks(projectPlanEntity.getTasks());
        }

        return projectPlanRepo.save(projectPlanEntityUpdate);
    }

    @Override
    public DeleteResponse deleteProject(int projectPlanId) {
        DeleteResponse deleteResponse = new DeleteResponse();

        if (!projectPlanRepo.existsById(projectPlanId)) {

            ProjectPlanEntity projectPlan = projectPlanRepo.findById(projectPlanId).orElseThrow(
                    () -> new RuntimeException(Constant.PROJECT_PLAN_DOES_NOT_EXISTS));

            if (!projectPlan.getTasks().isEmpty()) {
                for (TaskEntity task : projectPlan.getTasks()) {
                    task.setProjectPlanEntity(null);
                    taskService.updateTask(task.getId(), task);
                }
            }

            projectPlanRepo.deleteById(projectPlanId);
            deleteResponse.setId(projectPlanId);
            deleteResponse.setType(Constant.PROJECT_PLAN);
            deleteResponse.setMessage(message.setDeleteMessage(Constant.PROJECT_PLAN, projectPlanId));
        } else {
            deleteResponse.setMessage(Constant.PROJECT_PLAN_DOES_NOT_EXISTS);
        }

        return deleteResponse;
    }
}
