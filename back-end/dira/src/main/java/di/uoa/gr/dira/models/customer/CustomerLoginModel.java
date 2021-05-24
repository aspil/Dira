package di.uoa.gr.dira.models.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CustomerLoginModel {
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 50)
    private String username;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 8)
    private String password;
}
