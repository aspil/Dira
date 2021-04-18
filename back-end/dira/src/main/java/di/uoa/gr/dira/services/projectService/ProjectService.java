package di.uoa.gr.dira.services.projectService;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.models.project.ProjectUsersModel;
import di.uoa.gr.dira.repositories.CustomerRepository;
import di.uoa.gr.dira.repositories.ProjectRepository;
import di.uoa.gr.dira.services.BaseService;
import di.uoa.gr.dira.util.MapperHelper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProjectService extends BaseService<ProjectModel, Project, Long, ProjectRepository> implements IProjectService {
    CustomerRepository customerRepository;

    ProjectService(ProjectRepository repository, CustomerRepository customerRepository) {
        super(repository);
        this.customerRepository = customerRepository;
    }

    @Override
    public ProjectUsersModel findUsersByProjectId(Long id) {
        Project project = repository.findById(id).orElse(null);

        if (project != null) {
            List<CustomerModel> users = MapperHelper.mapList(mapper, project.getCustomers(), CustomerModel.class);
            ProjectUsersModel projectUsers = mapper.map(project, ProjectUsersModel.class);
            projectUsers.setUsers(users);
            return projectUsers;
        }
        return null;
    }

    @Override
    public void addUserToProjectWithId(Long id) {
        Project project = repository.findById(id).orElse(null);

        if (project != null) {
            Customer customer = customerRepository.findById(1L).orElse(null);
            project.getCustomers().add(customer);
            repository.save(project);
        }
    }
}