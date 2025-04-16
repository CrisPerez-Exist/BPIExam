package com.example.BPIExam.service;

import com.example.BPIExam.model.TaskEntity;
import com.example.BPIExam.response.DeleteResponse;
import com.example.BPIExam.response.TaskResponse;

import java.util.List;

public interface TaskService {

    public List<TaskEntity> getAllTasks ();

    public TaskEntity getTask(int taskId);

    public TaskEntity addTask(TaskEntity taskEntity);

    public TaskEntity updateTask(int taskId, TaskEntity taskEntity);

    public DeleteResponse deleteTask(int taskId);

    public List<TaskEntity> getTaskDependency(int taskId);
}
