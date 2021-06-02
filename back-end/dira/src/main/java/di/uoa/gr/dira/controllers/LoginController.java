package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.customer.CustomerLoginModel;
import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.security.JwtHelper;
import di.uoa.gr.dira.services.loginService.ILoginService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ResponseHeader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("login")
public class LoginController {
    private final ILoginService loginService;
    private final JwtHelper jwtHelper;

    public LoginController(ILoginService loginService, JwtHelper jwtHelper) {
        this.loginService = loginService;
        this.jwtHelper = jwtHelper;
    }

    @ApiOperation(
            value = "Authenticate user and produce JWT token",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            responseHeaders = @ResponseHeader(name = HttpHeaders.AUTHORIZATION, response = String.class)
    )
    @PostMapping
    public ResponseEntity<@Valid CustomerModel> login(@Valid @RequestBody CustomerLoginModel customerLoginModel) {
        try {
            CustomerModel customer = loginService.authenticateUser(
                    customerLoginModel.getUsername(),
                    customerLoginModel.getPassword()
            );

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, jwtHelper.generateToken(customer))
                    .body(customer);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
