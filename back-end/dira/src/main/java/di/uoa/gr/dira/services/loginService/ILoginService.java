package di.uoa.gr.dira.services.loginService;

import di.uoa.gr.dira.models.customer.CustomerModel;

public interface ILoginService {
    CustomerModel authenticateUser(String username, String password);
}
