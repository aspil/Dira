package di.uoa.gr.dira.models.issue;


import di.uoa.gr.dira.models.IModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class IssueModel implements IModel<Long> {
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
    private String name;
}
