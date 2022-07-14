package com.revature.taskmaster.task;

import com.revature.taskmaster.common.dtos.ResourceCreationResponse;
import com.revature.taskmaster.task.dtos.TaskRequestPayload;
import com.revature.taskmaster.task.dtos.TaskResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(produces = "application/json")
    public List<TaskResponsePayload> getAllTasks() {
        return taskService.fetchAllTasks();
    }

    @GetMapping(value = "/search", produces = "application/json")
    public List<TaskResponsePayload> findBy(@RequestParam Map<String, String> requestParams) {
        return taskService.search(requestParams);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResourceCreationResponse createNewTask(@RequestBody TaskRequestPayload newTaskInfo) {
        return taskService.createTask(newTaskInfo);
    }

}
