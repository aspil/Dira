package di.uoa.gr.dira.services.loginService;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import di.uoa.gr.dira.models.customer.CustomerModel;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements ILoginService {
    private final ModelMapper mapper;
    private final AuthenticationManager authenticationManager;

    public LoginService(AuthenticationManager authenticationManager, ModelMapper mapper) {
        this.authenticationManager = authenticationManager;
        this.mapper = mapper;
    }

    public CustomerModel authenticateUser(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        Customer customer = (Customer) auth.getPrincipal();
        return mapper.map(customer, CustomerModel.class);
    }
}
