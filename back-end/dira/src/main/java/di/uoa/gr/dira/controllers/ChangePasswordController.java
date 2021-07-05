package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.ChangePasswordModel;
import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.services.customerService.ICustomerService;
import di.uoa.gr.dira.services.loginService.ILoginService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
public class ChangePasswordController {
    private final ILoginService loginService;
    private final ICustomerService customerService;

    public ChangePasswordController(ILoginService loginService, ICustomerService customerService) {
        this.loginService = loginService;
        this.customerService = customerService;
    }

    @ApiOperation(
            value = "Change password",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PostMapping("changePassword")
    public ResponseEntity<Void> changePassword(@Valid ChangePasswordModel changePasswordModel) {
        CustomerModel customer = loginService.authenticateUser(
                changePasswordModel.getUsername(),
                changePasswordModel.getCurrentPassword()
        );

        customer.setPassword(changePasswordModel.getNewPassword());
        customerService.save(customer);

        return ResponseEntity.ok().build();
    }
}
