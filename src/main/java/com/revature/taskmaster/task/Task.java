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
    @Column(nullable = false, columnDefinition = "VARCHAR NOT NULL CHECK (LENGTH(title) >= 1 AND LENGTH(title) <= 50)")
    private String title;

    /** A full description of the task - must not be null or empty */
    @Column(nullable = false, columnDefinition = "VARCHAR NOT NULL CHECK (LENGTH(description) >= 1)")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    /** The point value of this task - must be greater or equal to than 0 and less than 100 if not null */
    @Column(name = "point_value", columnDefinition = "INT CHECK(point_value >= 0 AND point_value < 100)")
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

    public Task(String title, String description, Priority priority, User creator) {
        this();
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.pointValue = 0;
        this.state = State.UNASSIGNED;
        this.creator = creator;
    }

    public Task(String title, String description, Priority priority, State state, List<String> labels, User creator) {
        this(title, description, priority, creator);
        this.state = state;
        this.labels = labels;
    }

    public Task(String title, String description, Priority priority, int pointValue, State state, List<String> labels, User creator) {
        this(title, description, priority, state, labels, creator);
        this.pointValue = pointValue;
    }

    public Task(String title, String description, Priority priority, int pointValue, State state, List<String> labels, User creator, List<User> assignees) {
        this(title, description, priority, pointValue, state, labels, creator);
        this.assignees = assignees;
    }

    public Task(String title, String description, Priority priority, int pointValue, LocalDate dueDate, State state, List<String> labels, User creator, List<User> assignees) {
        this(title, description, priority, pointValue, state, labels, creator, assignees);
        this.dueDate = dueDate;
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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public int getPointValue() {
        return pointValue;
    }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
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
