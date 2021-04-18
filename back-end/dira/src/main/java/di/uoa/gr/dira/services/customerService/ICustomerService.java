package di.uoa.gr.dira.groovy.services.customerService;

import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.groovy.services.IService;

public interface ICustomerService extends IService<CustomerModel, Long> {
    boolean authenticateUser(String username, String password);
}
