package di.uoa.gr.dira.services.customerService;

import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.services.IService;

import java.util.List;

public interface ICustomerService extends IService<CustomerModel, Long> {
    boolean authenticateUser(String username, String password);
    CustomerModel findByUsername(String username);
    CustomerModel findByEmail(String email);
    void updatePlan(Long customerId);
    List<ProjectModel> getAllProjects(Long customerId);
}
