package di.uoa.gr.dira.security;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import di.uoa.gr.dira.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Configurable
@Service
public class DiraAuthenticationProvider implements AuthenticationProvider {
    private final CustomerRepository customerRepository;

    public DiraAuthenticationProvider(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new CustomerNotFoundException("username", username));

        if (!PasswordManager.encoder().matches(password, customer.getPassword())) {
            // We actually know that the password is incorrect but we don't
            // say that explicitly for security reasons :^)
            throw new BadCredentialsException("Wrong username or password");
        }

        return new UsernamePasswordAuthenticationToken(username, password);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
