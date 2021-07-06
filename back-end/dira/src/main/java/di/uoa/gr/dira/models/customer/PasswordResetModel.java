package di.uoa.gr.dira.models.customer;

import di.uoa.gr.dira.security.ValidPassword;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class PasswordResetModel {
    @NotNull
    @NotEmpty
    @NotBlank
    private String pin;

    @ValidPassword
    private String newPassword;
}
