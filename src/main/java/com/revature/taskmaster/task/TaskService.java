package com.revature.taskmaster.task;

import com.revature.taskmaster.common.datasource.EntitySearcher;
import com.revature.taskmaster.common.util.exceptions.ResourceNotFoundException;
import com.revature.taskmaster.task.dtos.TaskResponsePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepo;
    private final EntitySearcher entitySearcher;

    @Autowired
    public TaskService(TaskRepository taskRepo, EntitySearcher entitySearcher) {
        this.taskRepo = taskRepo;
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

}
