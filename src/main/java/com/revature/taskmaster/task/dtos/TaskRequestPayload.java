package com.revature.taskmaster.task.dtos;

import com.revature.taskmaster.common.datasource.ResourceMetadata;
import com.revature.taskmaster.common.util.web.validators.ValidatorMessageUtil;
import com.revature.taskmaster.common.util.web.validators.annotations.KnownPriorityLevel;
import com.revature.taskmaster.common.util.web.validators.annotations.KnownTaskState;
import com.revature.taskmaster.common.util.web.validators.groups.OnCreate;
import com.revature.taskmaster.common.util.web.validators.groups.OnUpdate;
import com.revature.taskmaster.task.Task;
import com.revature.taskmaster.user.User;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class TaskRequestPayload {

    @Null(
        message = ValidatorMessageUtil.PROVIDE_NO_ID_ON_CREATE,
        groups = OnCreate.class)
    @NotNull(
        message = ValidatorMessageUtil.ID_REQUIRED_ON_UPDATE,
        groups = OnUpdate.class)
    private String id;

    @Length(
        message = ValidatorMessageUtil.TASK_TITLE_REQUIREMENTS,
        min = 1,
        max = 50,
        groups = {
            OnCreate.class,
            OnUpdate.class})
    @NotNull(
        message = ValidatorMessageUtil.TASK_TITLE_REQUIRED_ON_CREATE,
        groups = OnCreate.class)
    private String title;

    @Length(
        message = ValidatorMessageUtil.TASK_DESC_REQUIREMENTS,
        min = 1,
        groups = {
            OnCreate.class,
            OnUpdate.class})
    @NotNull(
        message = ValidatorMessageUtil.TASK_DESC_REQUIRED_ON_CREATE,
        groups = OnCreate.class)
    private String description;

    @NotNull(groups = OnCreate.class)
    @KnownPriorityLevel(
        groups = {
            OnCreate.class,
            OnUpdate.class})
    private int priority;

    @Min(
        message = ValidatorMessageUtil.TASK_POINT_REQUIREMENTS,
        value = 1,
        groups = {
            OnCreate.class,
            OnUpdate.class})
    @Max(
        message = ValidatorMessageUtil.TASK_POINT_REQUIREMENTS,
        value = 100,
        groups = {
            OnCreate.class,
            OnUpdate.class})
    private int pointValue;

    @Future(
        message = ValidatorMessageUtil.TASK_DUE_DATE_REQUIREMENTS,
        groups = {
            OnCreate.class,
            OnUpdate.class})
    private LocalDate dueDate;

    @Null(
        message = ValidatorMessageUtil.PROVIDE_NO_EXPLICIT_TASK_STATE_ON_CREATE,
        groups = {OnCreate.class})
    @KnownTaskState(groups = {OnUpdate.class})
    private String state;

    @NotNull(
        message = ValidatorMessageUtil.TASK_LABELS_REQUIRED_ON_CREATE,
        groups = OnCreate.class)
    private List<String> labels;

    @Null(
        message = ValidatorMessageUtil.PROVIDE_NO_EXPLICIT_TASK_CREATOR_ON_CREATE,
        groups = OnCreate.class)
    @Null(
        message = ValidatorMessageUtil.PROVIDE_NO_TASK_CREATOR_ON_UPDATE,
        groups = OnUpdate.class)
    private String creatorId;

    private List<String> assigneeIds;

    public TaskRequestPayload() {
        this.assigneeIds = new ArrayList<>();
    }

    public Task extractResource() {

        Task task = new Task()
                            .setTitle(title)
                            .setDescription(description)
                            .setPriority(Task.Priority.fromValue(priority))
                            .setPointValue(pointValue)
                            .setDueDate(dueDate)
                            .setLabels(labels)
                            .setCreator(new User(creatorId))
                            .setAssignees(assigneeIds.stream().map(User::new).collect(Collectors.toList()));

        if (id == null) {
            task.setId(UUID.randomUUID().toString());
            task.setState(Task.State.UNASSIGNED);
            task.setMetadata(new ResourceMetadata());
            task.getMetadata().setActive(true);
        } else {
            task.setId(id);
            task.setState(Task.State.fromValue(state));
            task.getMetadata().setUpdatedDatetime(LocalDateTime.now());
        }

        return task;

    }

}
