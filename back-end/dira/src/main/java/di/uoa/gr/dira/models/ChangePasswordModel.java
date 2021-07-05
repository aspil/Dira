package di.uoa.gr.dira.models;

import di.uoa.gr.dira.security.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordModel {
    @NotNull
    @NotEmpty
    @NotBlank
    private String username;

    @ValidPassword
    private String currentPassword;

    @ValidPassword
    private String newPassword;
}
