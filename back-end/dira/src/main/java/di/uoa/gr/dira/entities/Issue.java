package di.uoa.gr.dira.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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

    private long epicId;
    private long typeId;
    private long statusId;
    private long labelId;
    private long commentId;
    private long assigneeId;
    private long reporterId;
    private long fixVersionId;
    private int priority;
    private String key;
    private String title;
    private String description;
    private Date created;
    private Date updated;
    private Date dueDate;
    private long estimatedTime;
    private long remainingTime;
    private long loggedTime;
    private String timeUnit;
    private boolean resolved;
}
