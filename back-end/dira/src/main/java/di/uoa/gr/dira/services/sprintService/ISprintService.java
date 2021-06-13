package di.uoa.gr.dira.services.sprintService;

import di.uoa.gr.dira.models.sprint.SprintModel;
import di.uoa.gr.dira.services.IService;

import java.util.List;

public interface ISprintService extends IService<SprintModel, Long> {
    List<SprintModel> findAllSprintsByProjectId(Long projectId);

    SprintModel createSprintWithProjectId(Long projectId, Long customerId, SprintModel sprintModel);

    SprintModel findSprintWithProjectId(Long projectId, Long customerId, Long sprintId);

    SprintModel updateSprintWithProjectId(Long projectId, Long customerId, Long sprintId, SprintModel sprintModel);

    void deleteSprintWithProjectId(Long projectId, Long customerId, Long sprintId);
}
