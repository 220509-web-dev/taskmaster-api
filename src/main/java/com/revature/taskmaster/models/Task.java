package com.revature.taskmaster.models;

import java.util.Objects;

/**
 * Represents a task record within the data source
 */
public class Task implements Comparable {

    /** A generated string of characters that is used to uniquely define a task record within the data source */
    private String id;

    /** A brief title for the task - must not be null or empty; maximum length of 50 characters */
    private String title;

    /** A full description of the task - must not be null or empty */
    private String description;

    /** The point value of this task - must be greater than 0 and less than 100 */
    private int pointValue;

    /** The user who created this task - must not be null and have a valid/known user record id **/
    private User creator;

    /** The user to whom this task is assigned - nullable, but if not null it must have a valid/known user record id */
    private User assignee;

    /** A label used to describe this task and assist with filtering queries - must contain at least one value */
    private String label;

    public Task() {
        super();
    }

    public Task(String title, String description, int pointValue, User creator, User assignee) {
        this.title = title;
        this.description = description;
        this.pointValue = pointValue;
        this.creator = creator;
        this.assignee = assignee;
    }

    public Task(String title, String description, int pointValue, String creatorId, String assigneeId, String label) {
        this(title, description, pointValue, new User(creatorId), new User(assigneeId));
        this.label = label;
    }

    public Task(String title, String description, int pointValue, User creator, User assignee, String label) {
        this(title, description, pointValue, creator, assignee);
        this.label = label;
    }

    public Task(String id, String title, String description, int pointValue, User creator, User assignee, String label) {
        this(title, description, pointValue, creator, assignee, label);
        this.id = id;
    }

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

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        if (o == null || getClass() != o.getClass()) return 1;
        Task task = (Task) o;
        if (getId() != null) {
            return getId().compareTo(task.getId());
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return pointValue == task.pointValue && Objects.equals(id, task.id) && Objects.equals(title, task.title) && Objects.equals(description, task.description) && Objects.equals(creator, task.creator) && Objects.equals(assignee, task.assignee) && Objects.equals(label, task.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, pointValue, creator, assignee, label);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pointValue=" + pointValue +
                ", creator=" + creator +
                ", assignee=" + assignee +
                ", label=" + label +
                '}';
    }

}
