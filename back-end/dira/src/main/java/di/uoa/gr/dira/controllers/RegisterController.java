package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.services.customerService.ICustomerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("register")
public class RegisterController {
    private final ICustomerService service;

    public RegisterController(ICustomerService service) {
        this.service = service;
    }

    @PostMapping()
    public CustomerModel registerCostumer(@Valid @RequestBody CustomerModel customerModel) {
        if (service.findByEmail(customerModel.getEmail()) != null) {
            return null;
        }
        if (service.findByUsername(customerModel.getUsername()) != null) {
            return null;
        }
        return service.save(customerModel);
    }
}
