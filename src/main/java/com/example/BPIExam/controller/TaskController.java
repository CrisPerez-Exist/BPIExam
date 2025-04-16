package com.example.BPIExam.controller;

import com.example.BPIExam.model.TaskEntity;
import com.example.BPIExam.response.DeleteResponse;
import com.example.BPIExam.response.TaskResponse;
import com.example.BPIExam.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/task/")
    public List<TaskEntity> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/task/{id}")
    public  TaskEntity getTask(
            @PathVariable  int id) {
        return taskService.getTask(id);
    }

    @PostMapping("/task/")
    public TaskEntity addTask(
            @RequestBody TaskEntity taskEntity) {
        return taskService.addTask(taskEntity);
    }

    @PatchMapping("/task/{id}")
    public  TaskEntity updateTask(
            @PathVariable int id,
            @RequestBody TaskEntity taskEntity) {
        return taskService.updateTask(id, taskEntity);
    }

    @DeleteMapping("/task/{id}")
    public DeleteResponse deleteTask(
            @PathVariable int id) {
        return taskService.deleteTask(id);
    }

    @GetMapping("/task/dependent/{id}")
    public List<TaskEntity> getTaskDependency(
            @PathVariable int id) {
        return taskService.getTaskDependency(id);
    }

}
