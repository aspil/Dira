package di.uoa.gr.dira.security;

import di.uoa.gr.dira.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider implements IJwtProvider {

    private static final Logger logger = Logger.getLogger(JwtProvider.class);

    private final String jwtSecret;

    private final int jwtExpirationTime;

    public JwtProvider(@Value("${app.jwt.secret}") String jwtSecret,  @Value("${app.jwt.expirationTime}") int jwtExpirationTime) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationTime = jwtExpirationTime;
    }

    public String generateToken(Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpirationTime);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

    }

    public String getCustomerUsernameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateJwt(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        return true;
    }
}
