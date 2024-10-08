package com.example.WebFlux.controller;

import com.example.WebFlux.dto.TaskRequest;
import com.example.WebFlux.dto.TaskResponse;
import com.example.WebFlux.entity.Task;
import com.example.WebFlux.mapper.TaskMapper;
import com.example.WebFlux.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMapper taskMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public Flux<TaskResponse> getAllTasks() {
        return taskService.getAllTasks().map(taskMapper::taskToTaskResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public Mono<TaskResponse> getTaskById(@PathVariable String id) {
        return taskService.getTaskById(id).map(taskMapper::taskToTaskResponse);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MANAGER')")
    public Mono<TaskResponse> createTask(@RequestBody TaskRequest request) {
        Task task = TaskMapper.INSTANCE.taskRequestToTask(request);
        return taskService.createTask(task).map(taskMapper::taskToTaskResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public Mono<TaskResponse> updateTask(@PathVariable String id, @RequestBody TaskRequest request) {
        Task task = TaskMapper.INSTANCE.taskRequestToTask(request);
        return taskService.updateTask(id, task).map(taskMapper::taskToTaskResponse);
    }

    @PutMapping("/{id}/observers/{observerId}")
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public Mono<TaskResponse> addObserver(@PathVariable String id, @PathVariable String observerId) {
        return taskService.addObserver(id, observerId).map(taskMapper::taskToTaskResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public Mono<Void> deleteTask(@PathVariable String id) {
        return taskService.deleteTask(id);
    }
}


