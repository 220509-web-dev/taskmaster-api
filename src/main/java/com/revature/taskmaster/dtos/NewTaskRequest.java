package com.revature.taskmaster.dtos;

import java.util.Objects;

public class NewTaskRequest {

    /** A generated string of characters that is used to uniquely define a task record within the data source */
    private String id;

    /** A brief title for the task - must not be null or empty; maximum length of 50 characters */
    private String title;

    /** A full description of the task - must not be null or empty */
    private String description;

    /** The point value of this task - must be greater than 0 and less than 100 */
    private int pointValue;

    /** The id of the user who created this task - must not be null and have a valid/known user record id **/
    private String creatorId;

    /** The id of the user to whom this task is assigned - nullable, but if not null it must have a valid/known user record id */
    private String assigneeId;

    /** A label used to describe this task and assist with filtering queries - must contain at least one value */
    private String label;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPointValue() {
        return pointValue;
    }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewTaskRequest that = (NewTaskRequest) o;
        return pointValue == that.pointValue && Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(creatorId, that.creatorId) && Objects.equals(assigneeId, that.assigneeId) && Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, pointValue, creatorId, assigneeId, label);
    }

    @Override
    public String toString() {
        return "NewTaskRequest{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pointValue=" + pointValue +
                ", creatorId='" + creatorId + '\'' +
                ", assigneeId='" + assigneeId + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
