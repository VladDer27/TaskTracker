package com.example.WebFlux.service;

import com.example.WebFlux.entity.Task;
import com.example.WebFlux.entity.User;
import com.example.WebFlux.repository.TaskRepository;
import com.example.WebFlux.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public Flux<Task> getAllTasks() {
        return taskRepository.findAll().flatMap(this::enrichTask);
    }

    public Mono<Task> getTaskById(String id) {
        return taskRepository.findById(id).flatMap(this::enrichTask);
    }

    public Mono<Task> createTask(Task task) {
        task.setCreatedAt(Instant.now());
        task.setUpdatedAt(Instant.now());
        return Mono.zip(
                userRepository.findById(task.getAuthorId()),
                userRepository.findById(task.getAssigneeId()),
                Flux.fromIterable(task.getObserverIds())
                        .flatMap(userRepository::findById)
                        .collectList()
        ).flatMap(tuple -> {
            task.setAuthor(tuple.getT1());
            task.setAssignee(tuple.getT2());
            task.setObservers(new HashSet<>(tuple.getT3()));
            return taskRepository.save(task);
        });
    }

    public Mono<Task> updateTask(String id, Task updatedTask) {
        return taskRepository.findById(id)
                .flatMap(existingTask -> {
                    existingTask.setName(updatedTask.getName());
                    existingTask.setDescription(updatedTask.getDescription());
                    existingTask.setStatus(updatedTask.getStatus());
                    existingTask.setAuthorId(updatedTask.getAuthorId());
                    existingTask.setAssigneeId(updatedTask.getAssigneeId());
                    existingTask.setObserverIds(updatedTask.getObserverIds());
                    existingTask.setUpdatedAt(Instant.now());

                    return Mono.zip(
                            userRepository.findById(existingTask.getAuthorId()),
                            userRepository.findById(existingTask.getAssigneeId()),
                            Flux.fromIterable(existingTask.getObserverIds())
                                    .flatMap(userRepository::findById)
                                    .collectList()
                    ).flatMap(tuple -> {
                        existingTask.setAuthor(tuple.getT1());
                        existingTask.setAssignee(tuple.getT2());
                        existingTask.setObservers(new HashSet<>(tuple.getT3()));
                        return taskRepository.save(existingTask);
                    });
                });
    }

    public Mono<Task> addObserver(String id, String observerId) {
        return taskRepository.findById(id)
                .flatMap(existingTask -> {
                    Set<String> ids = existingTask.getObserverIds();
                    ids.add(observerId);
                    existingTask.setObserverIds(ids);
                    existingTask.setUpdatedAt(Instant.now());
                    return Mono.zip(
                            userRepository.findById(existingTask.getAuthorId()),
                            userRepository.findById(existingTask.getAssigneeId()),
                            Flux.fromIterable(existingTask.getObserverIds())
                                    .flatMap(userRepository::findById)
                                    .collectList()
                    ).flatMap(tuple -> {
                        existingTask.setAuthor(tuple.getT1());
                        existingTask.setAssignee(tuple.getT2());
                        existingTask.setObservers(new HashSet<>(tuple.getT3()));
                        return taskRepository.save(existingTask);
                    });
                });
    }

    public Mono<Void> deleteTask(String id) {
        return taskRepository.deleteById(id);
    }

    private Mono<Task> enrichTask(Task task) {
        Mono<User> authorMono = userRepository.findById(task.getAuthorId());
        Mono<User> assigneeMono = userRepository.findById(task.getAssigneeId());
        Flux<User> observersFlux = Flux.fromIterable(task.getObserverIds())
                .flatMap(userRepository::findById);

        return Mono.zip(authorMono, assigneeMono, observersFlux.collectList())
                .map(tuple -> {
                    task.setAuthor(tuple.getT1());
                    task.setAssignee(tuple.getT2());
                    task.setObservers(Set.copyOf(tuple.getT3()));
                    return task;
                });
    }
}

