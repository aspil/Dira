package di.uoa.gr.dira.models;

import di.uoa.gr.dira.entities.Project;
import di.uoa.gr.dira.entities.User;

import java.util.List;

public class ProjectModel {
    String name;
    String description;
    List<User> users;

    public ProjectModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers(List<User> users) {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
