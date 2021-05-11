package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.models.customer.CustomerLoginModel;
import di.uoa.gr.dira.security.JwtProvider;
import di.uoa.gr.dira.services.customerService.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("login")
public class LoginController {

//    @Autowired
    AuthenticationManager authenticationManager;

//    @Autowired
    JwtProvider jwtProvider;

    @PostMapping()
    public String login(@Valid @RequestBody CustomerLoginModel customerLoginModel) {
//        return service.authenticateUser(customerLoginModel.getUsername(), customerLoginModel.getPassword());
        Authentication auth = authenticationManager.authenticate( // TODO: fix NullPointerException
                new UsernamePasswordAuthenticationToken(
                        customerLoginModel.getUsername(),
                        customerLoginModel.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        return jwtProvider.generateToken(auth);
    }
}
