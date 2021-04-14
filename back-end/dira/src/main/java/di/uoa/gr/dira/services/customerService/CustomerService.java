package di.uoa.gr.dira.services.customerService;

import di.uoa.gr.dira.entities.Customer;
import di.uoa.gr.dira.entities.SubscriptionPlan;
import di.uoa.gr.dira.models.CustomerLoginModel;
import di.uoa.gr.dira.models.CustomerModel;
import di.uoa.gr.dira.repositories.CustomerRepository;
import di.uoa.gr.dira.security.PasswordManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService implements ICustomerService {
    CustomerRepository repository;

    CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CustomerModel> findAll() {
        List<Customer> customers = repository.findAll();
        List<CustomerModel> models = new ArrayList<>();
        for (Customer customer : customers) {
            models.add(new CustomerModel(customer));
        }

        return models;
    }

    @Override
    public CustomerModel findById(Long id) {
        Customer customer = repository.findById(id).orElse(null);
        return customer != null ? new CustomerModel(customer) : null;
    }

    @Override
    public CustomerModel insert(CustomerModel customerModel) {
        Customer customer = new Customer();

        customer.setName(customerModel.getName());
        customer.setSurname(customerModel.getSurname());
        customer.setEmail(customerModel.getEmail());
        customer.setUsername(customerModel.getUsername());
        customer.setSubscriptionPlan(SubscriptionPlan.PREMIUM);

        String password = PasswordManager.encoder().encode(customerModel.getPassword());
        customer.setPassword(password);

        Customer newCustomer = repository.save(customer);
        return new CustomerModel(newCustomer);
    }

    @Override
    public CustomerModel update(CustomerModel customerModel) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(CustomerModel customerModel) {
    }

    @Override
    public void deleteAll(Iterable<? extends CustomerModel> userModels) {

    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public boolean authenticateUser(String username, String password) {
        Customer customer = repository.findByUsername(username).orElse(null);
        return customer != null && PasswordManager.encoder().matches(password, customer.getPassword());
    }
}
