package di.uoa.gr.dira.entities;

import java.util.List;

public class Project {
    long id;
    String key;
    String name;
    String description;
    List<Customer> customers; // TODO: Do we really need this?

    public Project(long id, String key, String name, String description, List<Customer> customers) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.description = description;
        this.customers = customers;
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

    public void setUsers(List<Customer> customers) {
        this.customers = customers;
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

    public List<Customer> getUsers() {
        return customers;
    }
}
