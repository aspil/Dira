package di.uoa.gr.dira.services.customerService;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.entities.customer.PasswordResetToken;
import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.exceptions.security.PasswordResetTokenException;
import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.models.customer.PasswordModel;
import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.repositories.CustomerRepository;
import di.uoa.gr.dira.repositories.PasswordResetTokenRepository;
import di.uoa.gr.dira.security.PasswordManager;
import di.uoa.gr.dira.services.BaseService;
import di.uoa.gr.dira.services.projectService.IProjectService;
import di.uoa.gr.dira.shared.SubscriptionPlanEnum;
import di.uoa.gr.dira.util.mapper.MapperHelper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService extends BaseService<CustomerModel, Customer, Long, CustomerRepository> implements ICustomerService {
    private final IProjectService projectService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public CustomerService(CustomerRepository repository,
                           IProjectService projectService,
                           PasswordResetTokenRepository passwordResetTokenRepository,
                           ModelMapper mapper) {
        super(repository, mapper);
        this.projectService = projectService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
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
    public CustomerModel createPasswordResetTokenForUser(String customerEmail, String token) {
        Customer customer = repository.findByEmail(customerEmail).orElseThrow(() -> new CustomerNotFoundException("email", customerEmail));
        PasswordResetToken myToken = new PasswordResetToken(token, customer);
        passwordResetTokenRepository.save(myToken);

        return mapper.map(customer, CustomerModel.class);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token)
                .map(resetToken -> isTokenExpired(resetToken) ? "expired" : null)
                .orElse("invalidToken");
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }

    @Override
    public void changeUserPassword(PasswordModel passwordModel) {
        Optional<Customer> customer = passwordResetTokenRepository.findByToken(passwordModel.getToken()).map(PasswordResetToken::getCustomer);
        if (customer.isPresent()) {
            customer.get().setPassword(PasswordManager.encoder().encode(passwordModel.getNewPassword()));
            repository.save(customer.get());
        }
        else {
            throw new PasswordResetTokenException("PasswordResetToken", passwordModel.getToken());
        }
    }

    @Override
    public boolean checkIfValidOldPassword(String oldPassword, String customerPassword) {
        return PasswordManager.encoder().matches(oldPassword, customerPassword);
    }
}
