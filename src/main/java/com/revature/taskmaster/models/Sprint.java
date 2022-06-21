package com.revature.taskmaster.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a sprint record within the data source
 */
public class Sprint {

    /** A generated string of characters that is used to uniquely define a user record within the data source */
    private String id;

    /** A concise designator for the sprint - must not be null or empty; maximum length of 25 characters */
    private String name;

    /** The date on which the sprint began or will begin - can be a past or present value; must not be null */
    private LocalDate startDate;

    /** The date on which the sprint began or will end - can be a past or present value; must not be null */
    private LocalDate endDate;

    /** The list of tasks that are planned to be completed within this sprint; can be empty */
    private List<Task> backlog;

    public Sprint() {
        this.id = UUID.randomUUID().toString();
        this.backlog = new ArrayList<>();
    }

    public Sprint(String name, LocalDate startDate, LocalDate endDate) {
        this();
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Sprint(String name, LocalDate startDate, LocalDate endDate, List<Task> backlog) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.backlog = backlog;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Task> getBacklog() {
        return backlog;
    }

    public void setBacklog(List<Task> backlog) {
        this.backlog = backlog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sprint sprint = (Sprint) o;
        return Objects.equals(id, sprint.id) && Objects.equals(name, sprint.name) && Objects.equals(startDate, sprint.startDate) && Objects.equals(endDate, sprint.endDate) && Objects.equals(backlog, sprint.backlog);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startDate, endDate, backlog);
    }

    @Override
    public String toString() {
        return "Sprint{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", backlog=" + backlog +
                '}';
    }

}
