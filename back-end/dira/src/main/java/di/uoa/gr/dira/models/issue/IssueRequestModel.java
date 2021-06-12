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

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class IssueRequestModel implements IModel<Long> {
    private Long id;

    @NotNull
    @Positive
    private Long projectId;

    private Long epicId;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 1)
    private String title;

    @NotNull
    private IssueTypeEnum type;

    @NotNull
    private IssueStatusEnum status;

    private List<String> labels;

//    private CustomerModel reporter;

    private IssuePriorityEnum priority;

    private String description;

//    private Date dueDate;
}
