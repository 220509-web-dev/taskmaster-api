package com.revature.taskmaster.user;

import javax.persistence.*;
import java.util.Objects;

/**
 * Represents a user record within the data source
 */
@Entity // tells our ORM (Object Relational Mapper; in our case is Hibernate) that this is an object that maps to a relational entity
@Table(name = "users") // optional annotation, used to specify a different name for the table that this entity maps to (otherwise it uses the class name)
public class User implements Comparable<User> {

    /** A generated string of characters that is used to uniquely define a user record within the data source */
    @Id
    @Column(name = "user_id", nullable = false, unique = true) // optional annotation, used to specify the name and constraints of a column
    private String id;

    /** The given name of the user - must not be null or empty */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /** The surname of the user - must not be null or empty */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /** The email address of the user - must be a valid email address and unique within the data source */
    @Column(name = "email", nullable = false, unique = true)
    private String emailAddress;

    /** The username of the user - must be at least three characters long and unique within the data source */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * A hashed version of the password to the user's account - must be at least 8 characters long
     * and include at least one uppercase character, one lowercase character, one number, and one
     * special character
     */
    @Column(nullable = false, columnDefinition = "VARCHAR CHECK (LENGTH(password) >= 8)")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public User() {
        super();
    }

    public User(String id) {
        this.id = id;
    }

    public User(String firstName, String lastName, String emailAddress, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.username = username;
        this.password = password;
    }


    public User(String firstName, String lastName, String emailAddress, String username, String password, Role role) {
        this(firstName, lastName, emailAddress, username, password);
        this.role = role;
    }

    public User(String id, String firstName, String lastName, String emailAddress, String username, String password, Role role) {
        this(firstName, lastName, emailAddress, username, password, role);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Users are compared by their ids.
     *
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(User o) {
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
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(emailAddress, user.emailAddress) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, emailAddress, username, password, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    public enum Role {
        ADMIN, MANAGER, DEV, TESTER, LOCKED;
    }

}