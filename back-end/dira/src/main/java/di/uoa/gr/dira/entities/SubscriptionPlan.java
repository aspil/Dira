package di.uoa.gr.dira.entities;

import di.uoa.gr.dira.shared.SubscriptionPlanEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity(name = "SUBSCRIPTION_PLAN")
public class SubscriptionPlan {
    @Id
    String id;

    @Enumerated(EnumType.STRING)
    SubscriptionPlanEnum plan;

    public SubscriptionPlan() {
    }

    public SubscriptionPlan(String id, SubscriptionPlanEnum plan) {
        this.id = id;
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
