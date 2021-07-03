package di.uoa.gr.dira.models.project;

import di.uoa.gr.dira.models.IModel;
import di.uoa.gr.dira.models.sprint.SprintModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@NoArgsConstructor
public class ProjectSprintsModel implements IModel<Long> {
    private Long id;

    @NotNull
    List<SprintModel> sprints;
}
