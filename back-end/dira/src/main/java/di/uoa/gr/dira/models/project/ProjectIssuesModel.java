package di.uoa.gr.dira.models.project;


import di.uoa.gr.dira.models.IModel;
import di.uoa.gr.dira.models.issue.IssueModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class ProjectIssuesModel implements IModel<Long> {
    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    List<IssueModel> issues;

}
