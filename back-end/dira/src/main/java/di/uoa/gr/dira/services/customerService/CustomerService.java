package di.uoa.gr.dira.services.customerService;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.customer.PasswordResetPin;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.repositories.CustomerRepository;
import di.uoa.gr.dira.repositories.PasswordResetPinRepository;
import di.uoa.gr.dira.services.BaseService;
import di.uoa.gr.dira.shared.SubscriptionPlanEnum;
import di.uoa.gr.dira.util.mapper.MapperHelper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService extends BaseService<CustomerModel, Customer, Long, CustomerRepository> implements ICustomerService {
    private final PasswordResetPinRepository passwordResetPinRepository;

    public CustomerService(CustomerRepository repository,
                           PasswordResetPinRepository passwordResetPinRepository,
                           ModelMapper mapper) {
        super(repository, mapper);
        this.passwordResetPinRepository = passwordResetPinRepository;
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
        Customer customer = repository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("id", customerId.toString()));

        customer.setSubscriptionPlanFromEnum(SubscriptionPlanEnum.PREMIUM);
        repository.save(customer);
    }

    @Override
    public List<ProjectModel> getCustomerProjects(Long customerId) {
        return repository.findById(customerId)
                .map(customer -> MapperHelper.<Project, ProjectModel>mapList(mapper, customer.getProjects(), ProjectModel.class))
                .orElse(null);
    }

    @Override
    public CustomerModel createResetPinForCustomer(String customerEmail, String pin) {
        Customer customer = repository.findByEmail(customerEmail).
                orElseThrow(() -> new CustomerNotFoundException("email", customerEmail));

        PasswordResetPin pinEntity = new PasswordResetPin(pin, customer);
        passwordResetPinRepository.save(pinEntity);

        return mapper.map(customer, CustomerModel.class);
    }

    @Override
    public Optional<CustomerModel> getCustomerByResetPin(String resetPin) {
        return passwordResetPinRepository.findByPin(resetPin)
                .map(pin -> mapper.map(pin.getCustomer(), CustomerModel.class));
    }

    @Override
    public String validatePasswordResetPin(String pin) {
        return passwordResetPinRepository.findByPin(pin)
                .map(resetPin -> isPinExpired(resetPin) ? "expired" : "")
                .orElse("invalidPin");
    }

    private boolean isPinExpired(PasswordResetPin passPin) {
        Calendar cal = Calendar.getInstance();
        return passPin.getExpiryDate().before(cal.getTime());
    }
}
