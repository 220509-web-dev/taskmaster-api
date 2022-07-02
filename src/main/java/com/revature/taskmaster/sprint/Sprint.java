package com.revature.taskmaster.sprint;

import com.revature.taskmaster.common.datasource.Resource;
import com.revature.taskmaster.project.Project;
import com.revature.taskmaster.task.Task;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(
    name = "sprints",
    uniqueConstraints = @UniqueConstraint(columnNames = {"name", "project_id"})
)
public class Sprint extends Resource {

    @Column(nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToMany
    @JoinTable(name = "sprint_tasks")
    private List<Task> tasks;

    public Sprint() {
        super();
        tasks = new ArrayList<>();
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTasks(Task... tasks) {
        if (this.tasks == null) {
            this.tasks = new ArrayList<>();
        }
        this.tasks.addAll(Arrays.asList(tasks));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sprint sprint = (Sprint) o;
        return Objects.equals(name, sprint.name) && Objects.equals(project, sprint.project) && Objects.equals(tasks, sprint.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, project, tasks);
    }

    @Override
    public String toString() {
        return "Sprint{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", projectId=" + project.getId() +
                ", taskIds=" + tasks.stream().map(Task::getId).collect(Collectors.toList()) +
                ", metadata=" + metadata +
                '}';
    }

}
