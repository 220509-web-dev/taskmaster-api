package com.revature.taskmaster.task.dtos;

import java.util.Objects;

public class NewTaskRequest {

    private String id;

    private String title;

    private String description;

    private int pointValue;

    private String creatorId;

    private String assigneeId;

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
