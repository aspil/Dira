package di.uoa.gr.dira.models.issue;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.issue.*;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.models.IModel;
import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.shared.IssuePriorityEnum;
import di.uoa.gr.dira.shared.IssueStatusEnum;
import di.uoa.gr.dira.shared.IssueTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class IssueResponseModel implements IModel<Long> {
    private Long id;

    private String key;

    private String title;

//    private ProjectModel project;
    private String projectName;

//    private IssueResponseModel epic;
//    private String epic;

    private IssueTypeEnum type;
//
    private IssueStatusEnum status;
//
//    private List<IssueLink> issueLinks;
//    private List<String> issueLinks;
//    private List<String> labels;
//
//    private List<IssueComment> comments;

//    private CustomerModel assignee;
//    private String assignee;

//    private CustomerModel reporter;
    private String reporter;
//    private List<IssueFixVersion> fixVersions;

    private IssuePriorityEnum priority;

    private String description;

    private Date created;

    private Date updated;

//    private Date dueDate;
//
//    private long estimatedTime;
//
//    private long remainingTime;
//
//    private long loggedTime;
//
//    private String timeUnit;

    private boolean resolved;
}
