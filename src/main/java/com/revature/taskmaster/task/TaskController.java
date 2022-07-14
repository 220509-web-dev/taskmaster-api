package com.revature.taskmaster.task;

import com.revature.taskmaster.common.dtos.ErrorResponse;
import com.revature.taskmaster.common.dtos.ResourceCreationResponse;
import com.revature.taskmaster.task.dtos.TaskRequestPayload;
import com.revature.taskmaster.task.dtos.TaskResponsePayload;
import io.swagger.v3.core.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @PostMapping("/task")
    public String addTask(@RequestBody Task newTask) {
        return taskService.addTask(newTask);
    }
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

    public ResourceCreationResponse createNewTask(TaskRequestPayload newTaskInfo) {
        // TODO implement new task creation/persistence handler
        return null;

    public String addTask(Task newTask)  {
        newTask.add(taskService);
    return "In Progress";
    }

}
