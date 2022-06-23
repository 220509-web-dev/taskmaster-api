package com.revature.taskmaster.task.dtos;

import com.revature.taskmaster.task.Task;
import com.revature.taskmaster.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TaskResponse {

    private String id;
    private String title;
    private String description;
    private int pointValue;
    private String creatorId;
    private List<String> assigneeIds;
    private String label;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.pointValue = task.getPointValue();
        this.creatorId = task.getCreator().getId();
        this.assigneeIds = task.getAssignees().stream().map(User::getId).collect(Collectors.toList());
        this.label = task.getLabel();
    }
}
