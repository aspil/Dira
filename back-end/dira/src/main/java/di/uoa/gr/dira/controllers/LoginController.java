package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.CustomerLoginModel;
import di.uoa.gr.dira.services.customerService.ICustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    ICustomerService service;

    public LoginController(ICustomerService service) {
        this.service = service;
    }

    @PostMapping("login")
    public boolean login(@RequestBody CustomerLoginModel customerLoginModel) {
        return service.authenticateUser(customerLoginModel);
    }
}
