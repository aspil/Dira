package di.uoa.gr.dira.entities;

import di.uoa.gr.dira.shared.SubscriptionPlanEnum;

import javax.persistence.*;

@Entity
public class SubscriptionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subscription_plan_id")
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    SubscriptionPlanEnum plan;

    public static SubscriptionPlan STANDARD = new SubscriptionPlan(1L, SubscriptionPlanEnum.STANDARD);
    public static SubscriptionPlan PREMIUM = new SubscriptionPlan(2L, SubscriptionPlanEnum.PREMIUM);

    public SubscriptionPlan() {
    }

    public SubscriptionPlan(Long id, SubscriptionPlanEnum plan) {
        this.id = id;
        this.plan = plan;
    }

    public SubscriptionPlan(SubscriptionPlanEnum plan) {
        this.plan = plan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubscriptionPlanEnum getPlan() {
        return plan;
    }

    public void setPlan(SubscriptionPlanEnum plan) {
        this.plan = plan;
    }
}
