package di.uoa.gr.dira.services.customerService;

import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.repositories.CustomerRepository;
import di.uoa.gr.dira.services.IService;

import java.util.List;
import java.util.Optional;

public interface ICustomerService extends IService<CustomerModel, Long, CustomerRepository> {
    /**
     * Searches for a Customer by their username
     *
     * @param username The username to search for
     * @return An Optional Customer Model. It will contain the CustomerModel if the customer is found or an empty Optional otherwise
     */
    Optional<CustomerModel> findByUsername(String username);

    /**
     * Searches for a Customer by their email
     *
     * @param email The email to search for
     * @return An Optional Customer Model. It will contain the CustomerModel if the customer is found or an empty Optional otherwise
     */
    Optional<CustomerModel> findByEmail(String email);

    /**
     * Updates the plan of a user to PREMIUM
     *
     * @param customerId The id of the customer whose plan is going to be updated
     */
    void updatePlan(Long customerId);

    /**
     * Retrieves the Projects of the Customer with the given `customerId`
     *
     * @param customerId The customerId to search for
     * @return A list of Projects for the given customer
     */
    List<ProjectModel> getCustomerProjects(Long customerId);

    CustomerModel createResetPinForCustomer(String customerEmail, String pin);

    Optional<CustomerModel> getCustomerByResetPin(String resetPin);

    String validatePasswordResetPin(String pin);
}
