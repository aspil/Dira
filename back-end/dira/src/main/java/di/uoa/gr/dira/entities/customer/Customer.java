package di.uoa.gr.dira.entities.customer;

import di.uoa.gr.dira.entities.project.Project;
import di.uoa.gr.dira.security.PasswordManager;
import di.uoa.gr.dira.shared.SubscriptionPlanEnum;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
public class Customer implements UserDetails {
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

    @Transient
    private boolean enabled = true;

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

    /**
     * Sets the password of the entity from the raw password
     * by using the `PasswordManager`
     *
     * @param rawPassword The raw password
     */
    public void setFromRawPassword(String rawPassword) {
        this.password = PasswordManager.encoder().encode(rawPassword);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}