package di.uoa.gr.dira.services.customerService;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.repositories.CustomerRepository;
import di.uoa.gr.dira.services.BaseService;
import di.uoa.gr.dira.services.projectService.IProjectService;
import di.uoa.gr.dira.shared.SubscriptionPlanEnum;
import di.uoa.gr.dira.util.mapper.MapperHelper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService extends BaseService<CustomerModel, Customer, Long, CustomerRepository> implements ICustomerService {
    IProjectService projectService;

    public CustomerService(CustomerRepository repository, IProjectService projectService, ModelMapper mapper) {
        super(repository, mapper);
        this.projectService = projectService;
    }

    @Override
    public Optional<CustomerModel> findByUsername(String username) {
        return repository.findByUsername(username).map(customer -> mapper.<CustomerModel>map(customer, modelType));
    }

    @Override
    public Optional<CustomerModel> findByEmail(String email) {
        return repository.findByEmail(email).map(customer -> mapper.<CustomerModel>map(customer, modelType));
    }

    @Override
    public void updatePlan(Long customerId) {
        repository.findById(customerId)
                .ifPresent(customer -> {
                    customer.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.PREMIUM);
                    repository.save(customer);
                });
    }

    @Override
    public List<ProjectModel> getCustomerProjects(Long customerId) {
        return repository.findById(customerId)
                .map(customer -> MapperHelper.<Project, ProjectModel>mapList(mapper, customer.getProjects(), ProjectModel.class))
                .orElse(null);
    }

    @Override
    public void deleteById(Long userId) {
        projectService.deleteUserFromAllProjects(userId);
        super.deleteById(userId);
    }
}
