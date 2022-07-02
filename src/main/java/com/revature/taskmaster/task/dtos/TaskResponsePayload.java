package com.revature.taskmaster.task.dtos;

import com.revature.taskmaster.common.dtos.ResourceMetadataPayload;
import com.revature.taskmaster.task.Task;
import com.revature.taskmaster.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TaskResponsePayload {

    private String id;
    private String title;
    private String description;
    private int priority;
    private int pointValue;
    private String dueDate;
    private String state;
    private String creatorId;
    private List<String> assigneeIds;
    private List<String> labels;

    private ResourceMetadataPayload metadata;

    public TaskResponsePayload(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.priority = task.getPriority().getValue();
        this.pointValue = task.getPointValue();
        this.dueDate = (task.getDueDate() != null) ? task.getDueDate().toString() : null;
        this.state = task.getState().name();
        this.creatorId = task.getCreator().getId();
        this.assigneeIds = task.getAssignees().stream().map(User::getId).collect(Collectors.toList());
        this.labels = task.getLabels();
        this.metadata = new ResourceMetadataPayload(task.getMetadata());
    }
}
