package di.uoa.gr.dira.models.project;

import di.uoa.gr.dira.models.IModel;
import di.uoa.gr.dira.shared.ProjectVisibility;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ProjectModel implements IModel<Long> {
    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 15)
    private String key;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    private String description;

    @NotNull
    private ProjectVisibility visibility;
}
