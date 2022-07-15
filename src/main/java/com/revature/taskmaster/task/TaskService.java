package com.revature.taskmaster.task;

import com.revature.taskmaster.common.datasource.EntitySearcher;
import com.revature.taskmaster.common.dtos.ResourceCreationResponse;
import com.revature.taskmaster.common.util.exceptions.ResourceNotFoundException;
import com.revature.taskmaster.common.util.exceptions.UnprocessableEntityException;
import com.revature.taskmaster.common.util.web.validators.groups.OnCreate;
import com.revature.taskmaster.task.dtos.TaskRequestPayload;
import com.revature.taskmaster.task.dtos.TaskResponsePayload;
import com.revature.taskmaster.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Validated
public class TaskService {

    private final TaskRepository taskRepo;
    private final UserService userService;
    private final EntitySearcher entitySearcher;

    @Autowired
    public TaskService(TaskRepository taskRepo, UserService userService, EntitySearcher entitySearcher) {
        this.taskRepo = taskRepo;
        this.userService = userService;
        this.entitySearcher = entitySearcher;
    }

    public List<TaskResponsePayload> fetchAllTasks() {
        return taskRepo.findAll()
                       .stream()
                       .map(TaskResponsePayload::new)
                       .collect(Collectors.toList());
    }

    public List<TaskResponsePayload> search(Map<String, String> requestParamMap) {
        if (requestParamMap.isEmpty()) return fetchAllTasks();
        Set<Task> tasks = entitySearcher.searchForEntity(requestParamMap, Task.class);
        if (tasks.isEmpty()) throw new ResourceNotFoundException();
        return tasks.stream()
                    .map(TaskResponsePayload::new)
                    .collect(Collectors.toList());
    }

    @Validated(OnCreate.class)
    public ResourceCreationResponse createTask(@Valid TaskRequestPayload newTaskRequest)  {

        if (!userService.isKnownUserId(newTaskRequest.getCreatorId())) {
            throw new UnprocessableEntityException("Could not persist task, no user found with the provided creatorId!");
        }

        newTaskRequest.getAssigneeIds().forEach(assigneeId -> {
            if (!userService.isKnownUserId(assigneeId)) {
                throw new UnprocessableEntityException("Could not persist task, no user found with the provided assigneeId!");
            }
        });

        Task newTask = newTaskRequest.extractResource();
        newTask.setId(UUID.randomUUID().toString());
        taskRepo.save(newTask);

        return new ResourceCreationResponse(newTask.getId());

    }

}
