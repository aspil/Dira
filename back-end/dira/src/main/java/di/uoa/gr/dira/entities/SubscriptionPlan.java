package di.uoa.gr.dira.entities;

import di.uoa.gr.dira.shared.SubscriptionPlanEnum;

import javax.persistence.*;

@Entity(name = "SUBSCRIPTION_PLAN")
public class SubscriptionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subscription_plan_id")
    String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    SubscriptionPlanEnum plan;

    public SubscriptionPlan() {
    }

    public SubscriptionPlan(SubscriptionPlanEnum plan) {
        this.plan = plan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SubscriptionPlanEnum getPlan() {
        return plan;
    }

    public void setPlan(SubscriptionPlanEnum plan) {
        this.plan = plan;
    }
}
