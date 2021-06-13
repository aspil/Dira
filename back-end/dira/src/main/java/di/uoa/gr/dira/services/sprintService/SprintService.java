package di.uoa.gr.dira.services.sprintService;


import di.uoa.gr.dira.entities.sprint.Sprint;
import di.uoa.gr.dira.models.sprint.SprintModel;
import di.uoa.gr.dira.repositories.*;
import di.uoa.gr.dira.services.BaseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintService extends BaseService<SprintModel, Sprint, Long, SprintRepository> implements ISprintService  {
    private final IssueRepository issueRepository;

    public SprintService(SprintRepository repository,
                        IssueRepository issueRepository,
                        ModelMapper mapper) {
        super(repository, mapper);
        this.issueRepository = issueRepository;
    }

    public List<SprintModel> findAllSprintsByProjectId(Long projectId) {
        return null;
    }

    public SprintModel createSprintWithProjectId(Long projectId, Long customerId, SprintModel sprintModel) {
        return null;
    }

    public SprintModel findSprintWithProjectId(Long projectId, Long customerId, Long sprintId) {
        return null;
    }

    public SprintModel updateSprintWithProjectId(Long projectId, Long customerId, Long sprintId, SprintModel sprintModel) {
        return null;
    }

    public void deleteSprintWithProjectId(Long projectId, Long customerId, Long sprintId) {
    }
}
