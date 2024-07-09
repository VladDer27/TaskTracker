package com.example.WebFlux.dto;

import com.example.WebFlux.entity.TaskStatus;
import lombok.Data;

import java.util.Set;

@Data
public class TaskRequest {
    private String name;
    private String description;
    private TaskStatus status;
    private String authorId;
    private String assigneeId;
    private Set<String> observerIds;
}
