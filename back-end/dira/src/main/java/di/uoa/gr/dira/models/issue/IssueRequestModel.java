package di.uoa.gr.dira.models.issue;


import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.issue.*;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.models.IModel;
import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.models.project.ProjectModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class IssueRequestModel implements IModel<Long> {
    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 1, max = 255)
    private String key;

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 1)
    private String title;

//    private IssueRequestModel epic;     // Should be issue id

//    private IssueType type;
//
//    private IssueStatus status;

    private List<String> labels;

//    private CustomerModel reporter;   // Should be customer id

    private int priority;

    private String description;

//    private Date dueDate;
}