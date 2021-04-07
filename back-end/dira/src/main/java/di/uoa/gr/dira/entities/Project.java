package di.uoa.gr.dira.entities;

import di.uoa.gr.dira.models.ProjectModel;

import javax.persistence.*;
import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    Long id;

    @Column(nullable = false, length = 125)
    String key;

    @Column(nullable = false)
    String name;

    String description;

    @ManyToMany
    @JoinTable(name = "project_customer",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    List<Customer> customers;

    public Project() {
    }

    public Project(long id, String key, String name, String description) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.description = description;
    }

    public Project(ProjectModel model) {
        this.key = model.getKey();
        this.name = model.getName();
        this.description = model.getDescription();
        this.customers = model.getCustomers();
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

    public void setId(Long id) {
        this.id = id;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
