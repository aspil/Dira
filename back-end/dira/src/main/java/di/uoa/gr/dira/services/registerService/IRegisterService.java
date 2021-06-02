package di.uoa.gr.dira.services.registerService;

import di.uoa.gr.dira.models.customer.CustomerModel;

public interface IRegisterService {
    /**
     * Registers a new Customer based on the given `customerModel`
     * @param customerModel The `CustomerModel` based on which we will register the new user
     * @return The updated `CustomerModel`
     */
    CustomerModel registerCustomer(CustomerModel customerModel);
}