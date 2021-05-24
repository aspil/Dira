package di.uoa.gr.dira.models.project;

import di.uoa.gr.dira.models.customer.CustomerModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class ProjectUsersModel {
    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    private String key;

    private List<CustomerModel> users;
}
