package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.customer.CustomerLoginModel;
import di.uoa.gr.dira.services.loginService.ILoginService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth/login")
public class LoginController {
    private final ILoginService loginService;

    public LoginController(ILoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping()
    public String login(@Valid @RequestBody CustomerLoginModel customerLoginModel) {
        Authentication auth = loginService.authenticateUser(customerLoginModel.getUsername(), customerLoginModel.getPassword())
                .orElseThrow(() -> new RuntimeException("Couldn't authenticate user"));

        SecurityContextHolder.getContext().setAuthentication(auth);
        return loginService.generateToken(auth);
    }
}
