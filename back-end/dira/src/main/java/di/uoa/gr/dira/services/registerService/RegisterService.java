package di.uoa.gr.dira.services.registerService;

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

    public CustomerModel registerCostumer(CustomerModel customerModel) {
        if (customerService.findByEmail(customerModel.getEmail()) != null) {
            logger.error("Email '" + customerModel.getEmail() + "' already exists.");
            return null;
        }

        if (customerService.findByUsername(customerModel.getUsername()) != null) {
            logger.error("Username '" + customerModel.getUsername() + "' already exists.");
            return null;
        }
        return customerService.save(customerModel);
    }
}
