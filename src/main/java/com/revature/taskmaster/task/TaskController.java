package com.revature.taskmaster.task;

import com.revature.taskmaster.task.dtos.TaskRequestPayload;
import com.revature.taskmaster.task.dtos.TaskResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(value = "/newTask", consumes = "application/json", produces =  "application/json") // reachable at /newTask
    public Task createNewTask(TaskRequestPayload newTaskInfo) {

        return createNewTask();
    }

}
