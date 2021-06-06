package di.uoa.gr.dira.entities.issue;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.shared.IssuePriorityEnum;
import di.uoa.gr.dira.shared.IssueStatusEnum;
import di.uoa.gr.dira.shared.IssueTypeEnum;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "epic_id")
    private Issue epic;

    private IssueTypeEnum type;

    private IssueStatusEnum status;

    @OneToMany
    @JoinColumn(name = "issue_link_id")
    private List<IssueLink> issueLinks;

    @ManyToMany
    @JoinTable(name = "issue_labels",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "issue_label_id")
    )
    private List<IssueLabel> labels;

    @OneToMany
    @JoinColumn(name = "issue_comment_id")
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

    private long estimatedTime;

    private long remainingTime;

    private long loggedTime;

//    @Column(nullable = false)
//    private String timeUnit;

    @ColumnDefault(value = "false")
    private boolean resolved;
}
