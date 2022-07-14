package com.revature.taskmaster.task;

import com.revature.taskmaster.common.datasource.EntitySearcher;
import com.revature.taskmaster.common.util.exceptions.ResourceNotFoundException;
import com.revature.taskmaster.common.util.web.validators.groups.OnCreate;
import com.revature.taskmaster.task.dtos.TaskRequestPayload;
import com.revature.taskmaster.task.dtos.TaskResponsePayload;
import com.revature.taskmaster.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
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
    public Task createTask(@Valid TaskRequestPayload newTaskRequest)  {
        // created task 1
        Task newTask = newTaskRequest.extractResource();
        newTask.setTitle("task-1");
        newTask.setDescription("Use what you've learned in Bash to construct a calculator [open documentation]");
        newTask.setPriority(Task.Priority.P2);
        newTask.setPointValue(15);
        newTask.setDueDate(LocalDate.MAX);
        newTask.setState(Task.State.READY_TO_START);
        newTask.setLabels("ready-to-start", "test");
        newTask.setCreator();
        return newTask;
    }

}
