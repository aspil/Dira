package di.uoa.gr.dira.services.registerService;

import di.uoa.gr.dira.exceptions.customer.CustomerAlreadyExistsException;
import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.services.customerService.ICustomerService;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

@Service
public class RegisterService implements IRegisterService {
    private static final Logger logger = Logger.getLogger(RegisterService.class);
    private final ICustomerService customerService;

    public RegisterService(ICustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerModel registerCustomer(CustomerModel customerModel) throws CustomerAlreadyExistsException {
        if (customerService.findByUsername(customerModel.getUsername()).isPresent()) {
            logger.error("Username '" + customerModel.getUsername() + "' already exists.");
            throw new CustomerAlreadyExistsException("username", customerModel.getUsername());
        }

        if (customerService.findByEmail(customerModel.getEmail()).isPresent()) {
            logger.error("Email '" + customerModel.getEmail() + "' already exists.");
            throw new CustomerAlreadyExistsException("email", customerModel.getEmail());
        }

        return customerService.save(customerModel);
    }
}
