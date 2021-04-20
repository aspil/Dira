package di.uoa.gr.dira.entities.customer;

import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.security.PasswordManager;
import di.uoa.gr.dira.shared.SubscriptionPlanEnum;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String surname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "subscription_plan_id")
    private SubscriptionPlan subscriptionPlan;

    @ManyToMany
    private List<Project> projects;

    /**
     * Sets the subscription plan based on the value of `SubscriptionPlanEnum` provided
     *
     * @param subscriptionPlan The subscription plan enum
     */
    public void setSubscriptionPlanFromEnum(SubscriptionPlanEnum subscriptionPlan) {
        switch (subscriptionPlan) {
            case PREMIUM:
                this.subscriptionPlan = SubscriptionPlan.PREMIUM;
                break;
            case STANDARD:
                this.subscriptionPlan = SubscriptionPlan.STANDARD;
                break;
        }
    }

    public void setFromRawPassword(String rawPassword) {
        this.password = PasswordManager.encoder().encode(rawPassword);
    }
}