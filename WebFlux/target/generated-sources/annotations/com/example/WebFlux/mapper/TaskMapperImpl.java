package com.example.WebFlux.mapper;

import com.example.WebFlux.dto.TaskRequest;
import com.example.WebFlux.dto.TaskResponse;
import com.example.WebFlux.entity.Task;
import com.example.WebFlux.entity.User;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-09T14:37:35+0300",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task taskRequestToTask(TaskRequest taskRequest) {
        if ( taskRequest == null ) {
            return null;
        }

        Task task = new Task();

        task.setName( taskRequest.getName() );
        task.setDescription( taskRequest.getDescription() );
        task.setStatus( taskRequest.getStatus() );
        task.setAuthorId( taskRequest.getAuthorId() );
        task.setAssigneeId( taskRequest.getAssigneeId() );
        Set<String> set = taskRequest.getObserverIds();
        if ( set != null ) {
            task.setObserverIds( new LinkedHashSet<String>( set ) );
        }

        return task;
    }

    @Override
    public TaskResponse taskToTaskResponse(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskResponse taskResponse = new TaskResponse();

        taskResponse.setId( task.getId() );
        taskResponse.setName( task.getName() );
        taskResponse.setDescription( task.getDescription() );
        taskResponse.setCreatedAt( task.getCreatedAt() );
        taskResponse.setUpdatedAt( task.getUpdatedAt() );
        taskResponse.setStatus( task.getStatus() );
        taskResponse.setAuthor( task.getAuthor() );
        taskResponse.setAssignee( task.getAssignee() );
        Set<User> set = task.getObservers();
        if ( set != null ) {
            taskResponse.setObservers( new LinkedHashSet<User>( set ) );
        }

        return taskResponse;
    }
}
