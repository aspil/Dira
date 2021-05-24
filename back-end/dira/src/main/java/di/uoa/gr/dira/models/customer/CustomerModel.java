package di.uoa.gr.dira.models.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import di.uoa.gr.dira.models.IModel;
import di.uoa.gr.dira.shared.SubscriptionPlanEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class CustomerModel implements IModel<Long> {
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

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 8)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull
    private SubscriptionPlanEnum subscriptionPlan;

    public String createJwtSubject() {
        return String.format("%d,%s,%s", id, username, subscriptionPlan.toString());
    }
}
