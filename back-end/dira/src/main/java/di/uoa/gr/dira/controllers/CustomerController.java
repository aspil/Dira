package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.models.project.ProjectModel;
import di.uoa.gr.dira.services.customerService.ICustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
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

    @ApiOperation(
            value = "Retrieves all the customers in the system",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @GetMapping
    public List<@Valid CustomerModel> getAllCustomers() {
        return service.findAll();
    }

    @ApiOperation(
            value = "Deletes all the users in the system",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @DeleteMapping
    public void deleteAllCustomers() {
        service.deleteAll();
    }

    @ApiOperation(
            value = "Retrieves a user by the id provided",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @GetMapping("{id}")
    public @Valid CustomerModel getCustomerById(@PathVariable Long id) {
        return service.findById(id).orElseThrow(() -> new CustomerNotFoundException("id", id.toString()));
    }

    @ApiOperation(
            value = "Deletes a user by the id provided",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @DeleteMapping("{id}")
    public void deleteCustomerById(@PathVariable Long id) {
        try {
            service.deleteById(id);
        } catch (EmptyResultDataAccessException ignored) {
            throw new CustomerNotFoundException("id", id.toString());
        }
    }

    @ApiOperation(
            value = "Updates the user's plan to PREMIUM by the given id",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PutMapping("{id}/update")
    public void updateCustomerIdPlan(@PathVariable Long id) {
        service.updatePlan(id);
    }

    @ApiOperation(
            value = "Retrieves all the projects that the user belongs to",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @GetMapping("{id}/projects")
    public List<@Valid ProjectModel> getCustomerProjects(@PathVariable Long id) {
        return service.getAllProjects(id);
    }
}
