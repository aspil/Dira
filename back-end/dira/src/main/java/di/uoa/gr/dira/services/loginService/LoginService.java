package di.uoa.gr.dira.services.loginService;

import di.uoa.gr.dira.security.JwtProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService implements ILoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public LoginService(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public Optional<Authentication> authenticateUser(String username, String password) {
        return Optional.ofNullable(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password)));
    }
    public String generateToken(Authentication auth) {
        return jwtProvider.generateToken(auth);
    }
}
