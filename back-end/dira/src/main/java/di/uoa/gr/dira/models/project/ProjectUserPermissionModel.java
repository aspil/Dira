package di.uoa.gr.dira.models.project;


import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.models.IModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ProjectUserPermissionModel implements IModel<Long> {
    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    private int permission;

    private Customer customer;
}
