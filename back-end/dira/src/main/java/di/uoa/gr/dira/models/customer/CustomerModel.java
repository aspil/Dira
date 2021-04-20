package di.uoa.gr.dira.models.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import di.uoa.gr.dira.models.IModel;
import di.uoa.gr.dira.shared.SubscriptionPlanEnum;

import javax.validation.constraints.*;

public class CustomerModel implements IModel<Long> {
    @NotNull
    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 50)
    private String username;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 50)
    private String surname;

    @NotNull
    @NotEmpty
    @NotBlank
    @Email
    private String email;

    @JsonIgnore
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 8)
    private String password;

    @NotNull
    private SubscriptionPlanEnum subscriptionPlan;

    public CustomerModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public SubscriptionPlanEnum getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlanEnum subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }
}
