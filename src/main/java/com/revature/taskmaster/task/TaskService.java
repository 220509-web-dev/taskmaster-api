package com.revature.taskmaster.task;

import com.revature.taskmaster.task.dtos.TaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepo;

    @Autowired
    public TaskService(TaskRepository taskRepo) {
        this.taskRepo = taskRepo;
    }

    public List<TaskResponse> fetchAllTasks() {
        return taskRepo.findAll()
                       .stream()
                       .map(TaskResponse::new)
                       .collect(Collectors.toList());
    }

}
