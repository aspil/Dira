package di.uoa.gr.dira.services.customerService;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.repositories.CustomerRepository;
import di.uoa.gr.dira.security.PasswordManager;
import di.uoa.gr.dira.services.BaseService;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends BaseService<CustomerModel, Customer, Long, CustomerRepository> implements ICustomerService {
    public CustomerService(CustomerRepository repository) {
        super(repository);
        CustomerModel.configureMapper(mapper);
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        Customer customer = repository.findByUsername(username).orElse(null);
        return customer != null && PasswordManager.encoder().matches(password, customer.getPassword());
    }

    @Override
    public CustomerModel findByUsername(String username) {
        return repository.findByUsername(username)
                .map(customer -> mapper.<CustomerModel>map(customer, modelType))
                .orElse(null);
    }
}
