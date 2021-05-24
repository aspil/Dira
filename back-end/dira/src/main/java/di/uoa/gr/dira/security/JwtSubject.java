package di.uoa.gr.dira.security;

import di.uoa.gr.dira.shared.SubscriptionPlanEnum;
import lombok.Data;

@Data
public class JwtSubject {
    private final Long id;
    private final String username;
    private final SubscriptionPlanEnum subscriptionPlan;

    public JwtSubject(String token) {
        String subject = JwtHelper.getTokenClaims(token).getSubject();
        String[] tokens = subject.split(",");
        this.id = Long.parseLong(tokens[0].trim());
        this.username = tokens[1].trim();
        this.subscriptionPlan = SubscriptionPlanEnum.valueOf(tokens[2].trim());
    }

    public static JwtSubject fromToken(String token) {
        return new JwtSubject(token);
    }
}