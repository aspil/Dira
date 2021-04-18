package di.uoa.gr.dira.services.customerService;

import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.services.IService;

public interface ICustomerService extends IService<CustomerModel, Long> {
    boolean authenticateUser(String username, String password);

    CustomerModel findByUsername(String username);
}
