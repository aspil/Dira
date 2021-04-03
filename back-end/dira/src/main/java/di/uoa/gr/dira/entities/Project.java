package di.uoa.gr.dira.entities;

import java.util.List;

public class Project {
    long id;
    String key;
    String name;
    String description;
    List<User> users; // TODO: Do we really need this?

    public Project(long id, String key, String name, String description, List<User> users) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.description = description;
        this.users = users;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<User> getUsers() {
        return users;
    }
}
