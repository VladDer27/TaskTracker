package com.example.WebFlux.mapper;

import com.example.WebFlux.dto.TaskRequest;
import com.example.WebFlux.dto.TaskResponse;
import com.example.WebFlux.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "author", ignore = true)
    @Mapping(target = "assignee", ignore = true)
    @Mapping(target = "observers", ignore = true)

    Task taskRequestToTask(TaskRequest taskRequest);

    TaskResponse taskToTaskResponse(Task task);
}

