package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.CustomerModel;
import di.uoa.gr.dira.services.customerService.ICustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class CustomerController {
    final ICustomerService service;

    public CustomerController(ICustomerService service) {
        this.service = service;
    }

    @GetMapping("all")
    @ResponseBody
    public List<CustomerModel> getAllCustomers() {
        return service.findAll();
    }

    @GetMapping("{id}")
    @ResponseBody
    public CustomerModel getCustomerById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseBody
    public CustomerModel createUser(@RequestBody CustomerModel user) {
        return service.insert(user);
    }

    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @DeleteMapping("all")
    public void deleteAllUsers() {
        service.deleteAll();
    }
}
