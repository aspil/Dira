package di.uoa.gr.dira.security;

import org.springframework.security.core.Authentication;

public interface IJwtProvider {
    String generateToken(Authentication auth);
    String getCustomerUsernameFromJwt(String token);
    boolean validateJwt(String token);
}
