package di.uoa.gr.dira.services.registerService;

import di.uoa.gr.dira.models.customer.CustomerModel;

public interface IRegisterService {
    CustomerModel registerCustomer(CustomerModel customerModel);
}
