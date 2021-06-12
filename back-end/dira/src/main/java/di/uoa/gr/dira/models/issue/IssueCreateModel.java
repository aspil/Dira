package di.uoa.gr.dira.models.issue;

import di.uoa.gr.dira.models.IModel;
import di.uoa.gr.dira.shared.IssuePriorityEnum;
import di.uoa.gr.dira.shared.IssueTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class IssueCreateModel implements IModel<Long> {
    private Long id;

    @Positive
    private Long epicId;

    @NotNull
    private IssueTypeEnum type;

    @Positive
    private Long assigneeId;

    @NotNull
    private IssuePriorityEnum priority;

    @NotNull
    @NotBlank
    @NotEmpty
    private String title;

    private String description;
}
