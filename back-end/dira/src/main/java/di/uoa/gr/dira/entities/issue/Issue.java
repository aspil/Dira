package di.uoa.gr.dira.entities.issue;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.shared.IssuePriorityEnum;
import di.uoa.gr.dira.shared.IssueStatusEnum;
import di.uoa.gr.dira.shared.IssueTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private Long id;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "epic_id")
    private Issue epic;

    @OneToMany(mappedBy = "epic")
    private List<Issue> epicIssues;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private IssueTypeEnum type;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    @ColumnDefault("0")
    private IssueStatusEnum status;

    @OneToMany(mappedBy = "issue")
    private List<IssueLink> issueLinks;

    @ManyToMany
    @JoinTable(name = "issue_labels",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "issue_label_id")
    )
    private List<IssueLabel> labels;

    @OneToMany(mappedBy = "issue")
    private List<IssueComment> comments;

    @OneToOne
    @JoinColumn(name = "assignee_id")
    private Customer assignee;

    @OneToOne
    @JoinColumn(name = "reported_id")
    private Customer reporter;

    @ManyToMany
    @JoinTable(name = "issue_fix_versions",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "issue_fix_version_id")
    )
    private List<IssueFixVersion> fixVersions;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private IssuePriorityEnum priority;

    @Column(nullable = false)
    private String key;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date updated;

    private Date dueDate;

    @ColumnDefault(value = "false")
    private boolean resolved;
}
