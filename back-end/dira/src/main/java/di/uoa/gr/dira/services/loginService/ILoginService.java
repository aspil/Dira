package di.uoa.gr.dira.services.loginService;

import org.springframework.security.core.Authentication;
import java.util.Optional;

public interface ILoginService {
    Optional<Authentication> authenticateUser(String username, String password);
    String generateToken(Authentication auth);
}
