package di.uoa.gr.dira.models;

import di.uoa.gr.dira.entities.Customer;
import di.uoa.gr.dira.entities.Project;

import java.util.List;

public class ProjectModel {
    String key;
    String name;
    String description;
    List<Customer> customers;

    public ProjectModel() {
    }

    public ProjectModel(Project project) {
        this.key = project.getKey();
        this.name = project.getName();
        this.description = project.getDescription();
        this.customers = project.getCustomers();
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
