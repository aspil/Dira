package di.uoa.gr.dira.models.customer;

import di.uoa.gr.dira.security.ValidPassword;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class PasswordModel {

    private String oldPassword;

    private  String token;

    @ValidPassword
    private String newPassword;

}
