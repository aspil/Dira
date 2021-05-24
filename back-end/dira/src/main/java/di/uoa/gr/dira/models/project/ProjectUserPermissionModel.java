package di.uoa.gr.dira.models.project;


import di.uoa.gr.dira.models.IModel;
import di.uoa.gr.dira.models.customer.CustomerModel;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ProjectUserPermissionModel implements IModel<Long> {
    private Long id;

    private int permission;

    private CustomerModel customer;
}
