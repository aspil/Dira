package di.uoa.gr.dira.services.sprintService;


import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.entities.sprint.Sprint;
import di.uoa.gr.dira.exceptions.commonExceptions.ActionNotPermittedException;
import di.uoa.gr.dira.exceptions.commonExceptions.CustomMessageException;
import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import di.uoa.gr.dira.exceptions.project.ProjectNotFoundException;
import di.uoa.gr.dira.exceptions.sprint.SprintDoesNotBelongToProjectException;
import di.uoa.gr.dira.models.sprint.SprintModel;
import di.uoa.gr.dira.repositories.*;
import di.uoa.gr.dira.services.BaseService;
import di.uoa.gr.dira.util.mapper.MapperHelper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintService extends BaseService<SprintModel, Sprint, Long, SprintRepository> implements ISprintService  {
    private final IssueRepository issueRepository;
    private final ProjectRepository projectRepository;
    private final CustomerRepository customerRepository;

    public SprintService(SprintRepository repository,
                        IssueRepository issueRepository,
                        ProjectRepository projectRepository,
                        CustomerRepository customerRepository,
                        ModelMapper mapper) {
        super(repository, mapper);
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
        this.customerRepository = customerRepository;
    }

    private Project checkPermissions(Long projectId, Long customerId) {
        customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("customerId", customerId.toString()));
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ProjectNotFoundException("projectId", projectId.toString()));

        project.
                getCustomers().
                stream().
                filter(customer1 -> customer1.getId().equals(customerId)).
                findFirst().
                orElseThrow(ActionNotPermittedException::new);

        return project;
    }

    public List<SprintModel> findAllSprintsByProjectId(Long projectId, Long customerId) {
        Project project = checkPermissions(projectId, customerId);

        if (project.getSprints().isEmpty()) {
            throw new CustomMessageException("There are no Sprints in this project!");
        }

        return MapperHelper.mapList(mapper, project.getSprints(), SprintModel.class);
    }

    public SprintModel createSprintWithProjectId(Long projectId, Long customerId, SprintModel sprintModel) {
        return null;
    }

    public SprintModel findSprintWithProjectId(Long projectId, Long customerId, Long sprintId) {
        Project project = checkPermissions(projectId, customerId);

        Sprint sprint = project.
                getSprints().
                stream().
                filter((sprint1 -> sprint1.getId().equals(sprintId))).
                findAny().
                orElseThrow(() -> new SprintDoesNotBelongToProjectException("sprintId", sprintId.toString()));

        return mapper.map(sprint, SprintModel.class);
    }

    public SprintModel updateSprintWithProjectId(Long projectId, Long customerId, Long sprintId, SprintModel sprintModel) {
        return null;
    }

    public void deleteSprintWithProjectId(Long projectId, Long customerId, Long sprintId) {
    }
}
