package di.uoa.gr.dira.models;

import di.uoa.gr.dira.entities.Customer;

import java.util.List;

public class ProjectModel {
    String name;
    String description;
    List<Customer> customers;

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

    public List<Customer> getUsers(List<Customer> customers) {
        return this.customers;
    }

    public void setUsers(List<Customer> customers) {
        this.customers = customers;
    }
}
