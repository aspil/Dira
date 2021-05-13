package di.uoa.gr.dira.security;

import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.shared.SubscriptionPlanEnum;
import io.jsonwebtoken.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtHelper {
    public String generateToken(CustomerModel customer) {
        return Jwts.builder()
                .setSubject(customer.createJwtSubject())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JwtConstants.EXPIRATION_TIME_MS))
                .signWith(SignatureAlgorithm.HS512, JwtConstants.SECRET)
                .compact();
    }

    public String getUsername(String token) {
        return JwtSubject.fromToken(token).getUsername();
    }

    public Date getExpirationDate(String token) {
        return getTokenClaims(token).getExpiration();
    }

    public SubscriptionPlanEnum getSubscriptionPlan(String token) {
        return JwtSubject.fromToken(token).getSubscriptionPlan();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(JwtConstants.SECRET).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private static Claims getTokenClaims(String token) {
        if (token.startsWith(JwtConstants.TOKEN_PREFIX)) {
            token = token.replace(JwtConstants.TOKEN_PREFIX, "");
        }

        return Jwts.parser()
                .setSigningKey(JwtConstants.SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    @Data
    private static class JwtSubject {
        private final String username;
        private final SubscriptionPlanEnum subscriptionPlan;

        private JwtSubject(String token) {
            String subject = getTokenClaims(token).getSubject();
            String[] tokens = subject.split(",");
            this.username = tokens[0].trim();
            this.subscriptionPlan = SubscriptionPlanEnum.valueOf(tokens[1].trim());
        }

        public static JwtSubject fromToken(String token) {
            return new JwtSubject(token);
        }
    }
}
