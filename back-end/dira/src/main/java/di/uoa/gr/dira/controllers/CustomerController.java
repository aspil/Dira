package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.services.customerService.ICustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class CustomerController {
    private final ICustomerService service;

    public CustomerController(ICustomerService service) {
        this.service = service;
    }

    @GetMapping("all")
    @ResponseBody
    public List<CustomerModel> getAllCustomers() {
        return service.findAll();
    }

    @DeleteMapping("all")
    public void deleteAllCustomers() {
        service.deleteAll();
    }

    @GetMapping("{id}")
    @ResponseBody
    public CustomerModel getCustomerById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("{id}")
    public void deleteCustomerById(@PathVariable Long id) {
        service.deleteById(id);
    }
}
