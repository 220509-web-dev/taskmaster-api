package com.revature.taskmaster.models;

import java.util.Objects;

/**
 * Represents a user record within the data source
 */
public class User implements Comparable {

    /** A generated string of characters that is used to uniquely define a user record within the data source */
    private String id;

    /** The given name of the user - must not be null or empty */
    private String firstName;

    /** The surname of the user - must not be null or empty */
    private String lastName;

    /** The email address of the user - must be a valid email address and unique within the data source */
    private String emailAddress;

    /** The username of the user - must be at least three characters long and unique within the data source */
    private String username;

    /** A hashed version of the password to the user's account - must be at least 8 characters long */
    private String password;

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

    public User(String id, String firstName, String lastName, String emailAddress, String username, String password) {
        this(firstName, lastName, emailAddress, username, password);
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

    /**
     * Users are compared by their ids.
     *
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        if (o == null || getClass() != o.getClass()) return 1;
        User user = (User) o;
        if (getId() != null) {
            return getId().compareTo(user.getId());
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(emailAddress, user.emailAddress) && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, emailAddress, username, password);
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
                '}';
    }

}