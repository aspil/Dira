package di.uoa.gr.dira.models.issue;

import di.uoa.gr.dira.models.IModel;
import di.uoa.gr.dira.shared.IssuePriorityEnum;
import di.uoa.gr.dira.shared.IssueStatusEnum;
import di.uoa.gr.dira.shared.IssueTypeEnum;
import di.uoa.gr.dira.util.LongStringPair;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class IssueModel implements IModel<Long> {
    @NotNull
    @Positive
    private Long id;

    @Positive
    private Long epicId;

    private List<LinkedIssueModel> epicLinks;

    @NotNull
    @NotEmpty
    @NotBlank
    private String key;

    @NotNull
    @NotEmpty
    @NotBlank
    private String title;

    private String description;

    @NotNull
    @NotBlank
    @NotEmpty
    private String projectName;

    @NotNull
    private IssueTypeEnum type;

    @NotNull
    private IssueStatusEnum status;

    private List<LongStringPair> labels;

    private List<LongStringPair> comments;

    private List<LongStringPair> fixVersions;

    private List<IssueLinkModel> issueLinks;

    @NotNull
    @NotEmpty
    @NotBlank
    private String reporter;

    private String assignee;

    @NotNull
    private IssuePriorityEnum priority;

    @NotNull
    private Date created;

    @NotNull
    private Date updated;

    private Date dueDate;

    private boolean resolved;
}
