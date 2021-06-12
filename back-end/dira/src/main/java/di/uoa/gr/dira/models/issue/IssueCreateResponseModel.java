package di.uoa.gr.dira.models.issue;

import di.uoa.gr.dira.models.IModel;
import di.uoa.gr.dira.shared.IssuePriorityEnum;
import di.uoa.gr.dira.shared.IssueStatusEnum;
import di.uoa.gr.dira.shared.IssueTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;

@Data
@NoArgsConstructor
public class IssueCreateResponseModel implements IModel<Long> {
    @NotNull
    @Positive
    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String key;

    @NotNull
    @NotEmpty
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    @NotEmpty
    private String projectName;

//    private IssueResponseModel epic;
//    private String epic;

    @NotNull
    private IssueTypeEnum type;

    @NotNull
    private IssueStatusEnum status;

//    private List<IssueLink> issueLinks;
//    private List<String> issueLinks;

//    private List<IssueLabel> labels;
//    private List<String> labels;

//    private List<IssueComment> comments;

//    private CustomerModel assignee;
//    private String assignee;

//    private CustomerModel reporter;
    @NotNull
    @NotEmpty
    @NotBlank
    private String reporter;

    private String assignee;

//    private List<IssueFixVersion> fixVersions;

    @NotNull
    private IssuePriorityEnum priority;

    private String description;

    @NotNull
    private Date created;

    @NotNull
    private Date updated;

    private Date dueDate;

    private boolean resolved;
}
