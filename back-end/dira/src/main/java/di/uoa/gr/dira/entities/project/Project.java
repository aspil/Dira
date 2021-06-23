package di.uoa.gr.dira.entities.project;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.issue.Issue;
import di.uoa.gr.dira.entities.sprint.Sprint;
import di.uoa.gr.dira.shared.ProjectVisibility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @Column(nullable = false, length = 15)
    private String key;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToMany
    @JoinTable(name = "project_customer",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private List<Customer> customers;

    @OneToMany(mappedBy="project", cascade = CascadeType.REMOVE)
    private List<Sprint> sprints;

    @OneToMany(mappedBy="project", cascade = CascadeType.REMOVE)
    private List<Issue> issues;

    @OneToMany(mappedBy="project", cascade = CascadeType.REMOVE)
    private List<Permission> permissions;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @ColumnDefault("0")
    private ProjectVisibility visibility;
}
