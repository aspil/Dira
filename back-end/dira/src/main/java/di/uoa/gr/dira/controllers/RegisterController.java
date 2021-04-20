package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.services.customerService.ICustomerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@Validated
public class RegisterController {
    private final ICustomerService service;

    public RegisterController(ICustomerService service) {
        this.service = service;
    }

    @PostMapping("register")
    public @Valid CustomerModel registerCostumer(@RequestBody @Valid CustomerModel customerModel) {
        return service.save(customerModel);
    }
}
