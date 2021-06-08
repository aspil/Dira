package di.uoa.gr.dira.entities.customer;

import di.uoa.gr.dira.shared.SubscriptionPlanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class SubscriptionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_plan_id")
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    SubscriptionPlanEnum plan;

    public static SubscriptionPlan STANDARD = new SubscriptionPlan(1L, SubscriptionPlanEnum.STANDARD);
    public static SubscriptionPlan PREMIUM = new SubscriptionPlan(2L, SubscriptionPlanEnum.PREMIUM);
}
