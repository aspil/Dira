package di.uoa.gr.dira.entities;

import javax.persistence.*;

@Entity(name = "project_customer")
public class ProjectCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_customer_id")
    private Long id;

    private Long project_id;
    private Long customer_id;

    public ProjectCustomer() {
    }

    public ProjectCustomer(Long id, Long project_id, Long customer_id) {
        this.id = id;
        this.project_id = project_id;
        this.customer_id = customer_id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    public Long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Long customer_id) {
        this.customer_id = customer_id;
    }
}
