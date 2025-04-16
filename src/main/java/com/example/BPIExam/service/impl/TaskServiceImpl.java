package com.example.BPIExam.service.impl;

import com.example.BPIExam.constants.Constant;
import com.example.BPIExam.helper.SetMessage;
import com.example.BPIExam.model.TaskEntity;
import com.example.BPIExam.repository.TaskEntityRepository;
import com.example.BPIExam.response.DeleteResponse;
import com.example.BPIExam.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskEntityRepository taskEntityRepo;

    @Autowired
    SetMessage message;

    @Override
    public List<TaskEntity> getAllTasks() {
        return taskEntityRepo.findAll();
    }

    @Override
    public TaskEntity getTask(int taskId) {
        return taskEntityRepo.findById(taskId).orElseThrow(
                () -> new RuntimeException(Constant.TASK_DOES_NOT_EXISTS));

    }

    @Override
    @Transactional
    public TaskEntity addTask(TaskEntity taskEntity) {
        return taskEntityRepo.save(taskEntity);
    }

    @Override
    @Transactional
    public TaskEntity updateTask(int taskId, TaskEntity taskEntity) {
        TaskEntity taskUpdate = taskEntityRepo.findById(taskId).orElseThrow(
                () -> new RuntimeException(Constant.TASK_DOES_NOT_EXISTS));

        if (!Objects.isNull(taskEntity.getTaskName())) {
            taskUpdate.setTaskName(taskEntity.getTaskName());
        }

        if (!Objects.isNull(taskEntity.getTaskDescription())) {
            taskUpdate.setTaskDescription(taskEntity.getTaskDescription());
        }

        if (taskEntity.getDuration() != 0) {
            taskUpdate.setDuration(taskEntity.getDuration());
        }

        if (!Objects.isNull(taskEntity.getTaskDependency())) {
            taskUpdate.setTaskDependency(taskEntity.getTaskDependency());
        }

        return taskEntityRepo.save(taskUpdate);
    }

    @Override
    public DeleteResponse deleteTask(int taskId) {
        DeleteResponse deleteResponse = new DeleteResponse();

        if (taskEntityRepo.existsById(taskId)) {
            List<TaskEntity> taskDependency = taskEntityRepo.findTaskDependent(taskId);

            if (!taskDependency.isEmpty()) {
                for (TaskEntity taskEntity : taskDependency) {
                    taskEntity.getTaskDependency()
                            .removeIf(taskEntity1 -> taskEntity1.getId() == taskId);
                    updateTask(taskEntity.getId(), taskEntity);
                }
            }

            taskEntityRepo.deleteById(taskId);
            deleteResponse.setId(taskId);
            deleteResponse.setType(Constant.TASK);
            deleteResponse.setMessage(message.setDeleteMessage(Constant.TASK, taskId));
        } else {
            deleteResponse.setMessage(Constant.TASK_DOES_NOT_EXISTS);
        }

        return deleteResponse;
    }

    @Override
    public List<TaskEntity> getTaskDependency(int taskId) {
        return taskEntityRepo.findTaskDependent(taskId);
    }
}
