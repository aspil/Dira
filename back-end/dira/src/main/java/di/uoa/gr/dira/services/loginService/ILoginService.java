package di.uoa.gr.dira.services.loginService;

import di.uoa.gr.dira.models.customer.CustomerModel;

public interface ILoginService {
    /**
     * Authenticates the Customer based on the given `username` and `password`
     * @param username The username of the Customer
     * @param password The password of the Customer
     * @return A `CustomerModel` if the user was authenticated successfully
     */
    CustomerModel authenticateUser(String username, String password);
}
