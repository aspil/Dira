package di.uoa.gr.dira.security;

import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.shared.SubscriptionPlanEnum;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtHelper {
    public String generateToken(CustomerModel customer) {
        return Jwts.builder()
                .setSubject(customer.createJwtSubject())
                .setIssuedAt(new Date())
                .setExpiration(makeExpirationDate(JwtConstants.EXPIRATION_TIME_MS))
                .signWith(SignatureAlgorithm.HS512, JwtConstants.SECRET)
                .compact();
    }

    public JwtSubject createSubject(String token) {
        return JwtSubject.fromToken(token);
    }

    public Long getId(String token) {
        return JwtSubject.fromToken(token).getId();
    }

    public String getUsername(String token) {
        return JwtSubject.fromToken(token).getUsername();
    }

    public Date makeExpirationDate(String token) {
        return getTokenClaims(token).getExpiration();
    }

    public SubscriptionPlanEnum getSubscriptionPlan(String token) {
        return JwtSubject.fromToken(token).getSubscriptionPlan();
    }

    public void refreshExpirationTime(String token) {
        getTokenClaims(token).setExpiration(makeExpirationDate(JwtConstants.EXPIRATION_TIME_MS));
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(JwtConstants.SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static Claims getTokenClaims(String token) {
        if (token.startsWith(JwtConstants.TOKEN_PREFIX)) {
            token = token.replace(JwtConstants.TOKEN_PREFIX, "");
        }

        return Jwts.parser()
                .setSigningKey(JwtConstants.SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    private Date makeExpirationDate(long expirationMs) {
        return new Date(System.currentTimeMillis() + expirationMs);
    }
}
