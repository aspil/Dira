package di.uoa.gr.dira.services.loginService;

import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.services.customerService.ICustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginService {
    private final ModelMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final ICustomerService customerService;

    public LoginService(ModelMapper mapper, AuthenticationManager authenticationManager, ICustomerService customerService) {
        this.authenticationManager = authenticationManager;
        this.customerService = customerService;
        this.mapper = mapper;
    }

    public CustomerModel authenticateUser(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // This is guaranteed to have a customer
        return customerService.findByUsername(auth.getPrincipal().toString()).get();
    }
}
