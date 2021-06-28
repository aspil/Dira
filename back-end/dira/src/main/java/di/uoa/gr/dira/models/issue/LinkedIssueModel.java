package di.uoa.gr.dira.models.issue;

import di.uoa.gr.dira.models.IModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkedIssueModel implements IModel<Long> {
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
}
