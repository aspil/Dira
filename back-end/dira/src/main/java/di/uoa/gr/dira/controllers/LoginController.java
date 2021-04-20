package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.customer.CustomerLoginModel;
import di.uoa.gr.dira.services.customerService.ICustomerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class LoginController {
    private final ICustomerService service;

    public LoginController(ICustomerService service) {
        this.service = service;
    }

    @PostMapping("login")
    public boolean login(@RequestBody CustomerLoginModel customerLoginModel) {
        return service.authenticateUser(customerLoginModel.getUsername(), customerLoginModel.getPassword());
    }
}
