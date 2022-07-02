package com.revature.taskmaster.task.dtos;

import com.revature.taskmaster.common.util.web.validators.ValidatorMessageUtil;
import com.revature.taskmaster.common.util.web.validators.annotations.KnownPriorityLevel;
import com.revature.taskmaster.common.util.web.validators.annotations.KnownTaskState;
import com.revature.taskmaster.common.util.web.validators.groups.OnCreate;
import com.revature.taskmaster.common.util.web.validators.groups.OnUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
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
                OnUpdate.class
        })
    @NotNull(
        message = ValidatorMessageUtil.TASK_TITLE_REQUIRED_ON_CREATE,
        groups = OnCreate.class)
    private String title;

    @Length(
            message = ValidatorMessageUtil.TASK_DESC_REQUIREMENTS,
            min = 1,
            groups = {
                    OnCreate.class,
                    OnUpdate.class
            })
    @NotNull(
            message = ValidatorMessageUtil.TASK_DESC_REQUIRED_ON_CREATE,
            groups = OnCreate.class)
    private String description;

    @KnownPriorityLevel
    @NotNull(groups = {OnCreate.class})
    private int priority;

    @Min(
        message = ValidatorMessageUtil.TASK_POINT_REQUIREMENTS,
        value = 1,
        groups = {
            OnCreate.class,
            OnUpdate.class
    })
    @Max(
        message = ValidatorMessageUtil.TASK_POINT_REQUIREMENTS,
        value = 100,
        groups = {
            OnCreate.class,
            OnUpdate.class
    })
    private int pointValue;

    @Future(
        message = ValidatorMessageUtil.TASK_DUE_DATE_REQUIREMENTS,
        groups = {
            OnCreate.class,
            OnUpdate.class
    })
    private LocalDate dueDate;

    @KnownTaskState
    private String state;

    @NotNull(
        message = ValidatorMessageUtil.TASK_LABELS_REQUIRED_ON_CREATE,
        groups = {OnCreate.class})
    private List<String> labels;

    private String creatorId;

    private List<String> assigneeIds;


}
