package com.revature.taskmaster.task;

import com.revature.taskmaster.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a task record within the data source
 */
@Entity
@Table(name = "tasks")
public class Task implements Comparable<Task> {

    /** A generated string of characters that is used to uniquely define a task record within the data source */
    @Id
    @Column(name = "task_id", nullable = false, unique = true)
    private String id;

    /** A brief title for the task - must not be null or empty; maximum length of 50 characters */
    @Column(columnDefinition = "VARCHAR NOT NULL CHECK (LENGTH(title) <= 50)")
    private String title;

    /** A full description of the task - must not be null or empty */
    @Column(nullable = false)
    private String description;

    /** The point value of this task - must be greater than 0 and less than 100 */
    @Column(name = "point_value", columnDefinition = "INT CHECK(point_value > 0 AND point_value < 100)")
    private int pointValue;

    /** The user who created this task - must not be null and have a valid/known user record id **/
    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    /** The users to whom this task is assigned - nullable, but if not null it must have a valid/known user record id */
    @ManyToMany
    @JoinTable(
        name = "task_assignees",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> assignees;

    /** A label used to describe this task and assist with filtering queries - must not be null */
    @Column(nullable = false)
    private String label;

    public Task() {
        super();
        this.id = UUID.randomUUID().toString();
    }

    public Task(String title, String description, int pointValue, User creator, List<User> assignees) {
        this();
        this.title = title;
        this.description = description;
        this.pointValue = pointValue;
        this.creator = creator;
        this.assignees = assignees;
    }

    public Task(String title, String description, int pointValue, String creatorId, String label) {
        this(title, description, pointValue, new User(creatorId), new ArrayList<>());
        this.label = label;
    }

    public Task(String title, String description, int pointValue, User creator, String label) {
        this(title, description, pointValue, creator, new ArrayList<>());
        this.label = label;
    }

    public Task(String title, String description, int pointValue, User creator, List<User> assignees, String label) {
        this();
        this.title = title;
        this.description = description;
        this.pointValue = pointValue;
        this.creator = creator;
        this.assignees = assignees;
        this.label = label;
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

    public List<User> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<User> assignees) {
        this.assignees = assignees;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public int compareTo(Task o) {
        if (this == o) return 0;
        if (getId() != null) {
            return getId().compareTo(o.getId());
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return pointValue == task.pointValue &&
                Objects.equals(id, task.id) &&
                Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                Objects.equals(creator, task.creator) &&
                Objects.equals(assignees, task.assignees) &&
                Objects.equals(label, task.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, pointValue, creator, assignees, label);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pointValue=" + pointValue +
                ", creator=" + creator +
                ", assignees=" + assignees +
                ", label=" + label +
                '}';
    }

}
