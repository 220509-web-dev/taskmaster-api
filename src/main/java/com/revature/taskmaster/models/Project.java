package com.revature.taskmaster.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a project record within the data source
 */
public class Project {

    /** A generated string of characters that is used to uniquely define a user record within the data source */
    private String id;

    /** A concise designator for the sprint - must not be null or empty; maximum length of 25 characters */
    private String name;

    /** The list of sprints that are associated with this project */
    private List<Sprint> sprints;

    /** The team of developers working on this project */
    private List<User> team;

    public Project() {
        this.id = UUID.randomUUID().toString();
        this.sprints = new ArrayList<>();
        this.team = new ArrayList<>();
    }

    public Project(String name) {
        this();
        this.name = name;
    }

    public Project(String name, List<Sprint> sprints, List<User> team) {
        this(name);
        this.sprints = sprints;
        this.team = team;
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

    public List<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }

    public List<User> getTeam() {
        return team;
    }

    public void setTeam(List<User> team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id) && Objects.equals(name, project.name) && Objects.equals(sprints, project.sprints) && Objects.equals(team, project.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sprints, team);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", sprints=" + sprints +
                ", team=" + team +
                '}';
    }

}
