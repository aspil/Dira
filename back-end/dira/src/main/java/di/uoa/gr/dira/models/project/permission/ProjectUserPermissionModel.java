package di.uoa.gr.dira.models.project.permission;

import di.uoa.gr.dira.models.IModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class ProjectUserPermissionModel implements IModel<Long> {
    Long id;

    @NotNull
    @Positive
    Long customerId;

    @Positive
    int permission;
}
