package com.revature.taskmaster.task;

import com.revature.taskmaster.task.dtos.TaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<TaskResponse> getAllTasks() {
        return taskService.fetchAllTasks();
    }

    @GetMapping(value = "/search", produces = "application/json")
    public List<TaskResponse> findBy(@RequestParam Map<String, String> requestParams) {
        return taskService.search(requestParams);
    }

}
