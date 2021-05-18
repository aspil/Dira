package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.services.registerService.IRegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("register")
public class RegisterController {
    private final IRegisterService service;

    public RegisterController(IRegisterService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @Valid CustomerModel registerCustomer(@Valid @RequestBody CustomerModel customerModel) {
        return service.registerCustomer(customerModel);
    }
}
