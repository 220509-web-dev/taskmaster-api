package com.revature.taskmaster.task;

import com.revature.taskmaster.common.datasource.Resource;
import com.revature.taskmaster.common.datasource.ResourceMetadata;
import com.revature.taskmaster.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a task record within the data source
 */
@Entity
@Table(name = "tasks")
public class Task extends Resource implements Comparable<Task> {

    /** A brief title for the task - must not be null or empty; maximum length of 50 characters */
    @Column(nullable = false)
    private String title;

    /** A full description of the task - must not be null or empty */
    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    /** The point value of this task - must be greater or equal to than 0 and less than 100 if not null */
    @Column(name = "point_value")
    private int pointValue;

    /** The date when this task is expected to be completed by - nullable*/
    @Column(name = "due_date")
    private LocalDate dueDate;

    /** The status of this task */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private State state;

    /** Descriptive strings used to assist with filtering task queries - must not be empty */
    @ElementCollection
    private List<String> labels;

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

    public Task() {
        super();
        this.id = UUID.randomUUID().toString();
        this.labels = new ArrayList<>();
        this.assignees = new ArrayList<>();
        this.metadata = new ResourceMetadata();
    }

    public String getTitle() {
        return title;
    }

    public Task setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public Priority getPriority() {
        return priority;
    }

    public Task setPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public int getPointValue() {
        return pointValue;
    }

    public Task setPointValue(int pointValue) {
        this.pointValue = pointValue;
        return this;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Task setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public State getState() {
        return state;
    }

    public Task setState(State state) {
        this.state = state;
        return this;
    }

    public List<String> getLabels() {
        return labels;
    }

    public Task setLabels(List<String> labels) {
        this.labels = labels;
        return this;
    }

    public User getCreator() {
        return creator;
    }

    public Task setCreator(User creator) {
        this.creator = creator;
        return this;
    }

    public List<User> getAssignees() {
        return assignees;
    }

    public Task setAssignees(List<User> assignees) {
        this.assignees = assignees;
        return this;
    }

    @Override
    public int compareTo(Task o) {
        if (this == o) return 0;
        if (getId() != null) {
            return (getPriority().getValue() < o.getPriority().getValue()) ? 1 : -1;
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
                Objects.equals(title, task.title) &&
                Objects.equals(description, task.description) &&
                priority == task.priority &&
                Objects.equals(dueDate, task.dueDate) &&
                state == task.state &&
                Objects.equals(labels, task.labels) &&
                Objects.equals(creator, task.creator) &&
                Objects.equals(assignees, task.assignees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, priority, pointValue, dueDate, state, labels, creator, assignees);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", pointValue=" + pointValue +
                ", dueDate=" + dueDate +
                ", state=" + state +
                ", labels=" + labels +
                ", creatorId=" + creator.getId() +
                ", assigneeIds=" + assignees.stream().map(User::getId).collect(Collectors.toList()) +
                ", metadata=" + metadata +
                '}';
    }

    public enum State {

        UNASSIGNED, READY_TO_START, IN_PROGRESS, FOR_REVIEW, DONE, BLOCKED;

        public static State fromValue(String value) {
            return Arrays.stream(State.values())
                    .filter(s -> s.name().equals(value.toUpperCase().replace(" ", "_")))
                    .findFirst()
                    .orElse(null);
        }

    }

    public enum Priority {

        P1(1), P2(2), P3(3), P4(4);

        private int value;

        Priority(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Priority fromValue(int value) {
            return Arrays.stream(Priority.values())
                         .filter(p -> p.getValue() == value)
                         .findFirst()
                         .orElse(null);
        }

    }

}
