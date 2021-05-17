package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.services.customerService.ICustomerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("users")
public class CustomerController {
    private final ICustomerService service;

    public CustomerController(ICustomerService service) {
        this.service = service;
    }

    @GetMapping
    public List<@Valid CustomerModel> getAllCustomers() {
        return service.findAll();
    }

    @DeleteMapping
    public void deleteAllCustomers() {
        service.deleteAll();
    }

    @GetMapping("{id}")
    public @Valid CustomerModel getCustomerById(@PathVariable Long id) {
        CustomerModel customerModel = service.findById(id);
        if (customerModel == null) {
            throw new CustomerNotFoundException("id", id.toString());
        }
        return customerModel;
    }

    @DeleteMapping("{id}")
    public void deleteCustomerById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @PutMapping("{id}/update")
    public void updateCustomerIdPlan(@PathVariable Long id) {
        service.updatePlan(id);
    }

    @GetMapping("{id}/projects")
    public List<@Valid ProjectModel> getCustomerProjects(@PathVariable Long id) {
        return service.getAllProjects(id);
    }
}
