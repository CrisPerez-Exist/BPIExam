package com.example.BPIExam.helper;

import com.example.BPIExam.model.ProjectPlanEntity;
import com.example.BPIExam.model.TaskEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CalculateSchedule {

    public ProjectPlanEntity calculateSchedule(ProjectPlanEntity projectPlan) {

        LocalDateTime projectStart = LocalDateTime.MAX;
        LocalDateTime projectEnd = LocalDateTime.MIN;

        for (TaskEntity task : projectPlan.getTasks()) {

            LocalDateTime taskStart = calculateTaskStart(task);
            LocalDateTime taskEnd = taskStart.plusMinutes(task.getDuration());

            task.setStartDate(taskStart);
            task.setEndDate(taskEnd);

            if (taskStart.isBefore(projectStart)) {
                projectStart = taskStart;
            }

            if (taskEnd.isAfter(projectEnd)) {
                projectEnd = taskEnd;
            }
        }

        projectPlan.setStartDate(projectStart);
        projectPlan.setEndDate(projectEnd);

        return projectPlan;

    }

    private LocalDateTime calculateTaskStart(TaskEntity task) {

        LocalDateTime endDate = LocalDateTime.MIN;

        for(TaskEntity taskDependency : task.getTaskDependency()) {
            if(taskDependency.getEndDate() != null
            && taskDependency.getEndDate().isAfter(endDate)) {
                endDate = taskDependency.getEndDate();
            }
        }

        if(endDate.equals(LocalDateTime.MIN)) {
            endDate = LocalDateTime.now();
        }

        return endDate;
    }
}
