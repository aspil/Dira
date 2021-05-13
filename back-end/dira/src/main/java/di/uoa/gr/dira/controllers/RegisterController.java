package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.services.registerService.IRegisterService;
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
    private final IRegisterService service;

    public RegisterController(IRegisterService service) {
        this.service = service;
    }

    @PostMapping()
    public CustomerModel registerCostumer(@Valid @RequestBody CustomerModel customerModel) {
       return service.registerCostumer(customerModel);
    }
}
