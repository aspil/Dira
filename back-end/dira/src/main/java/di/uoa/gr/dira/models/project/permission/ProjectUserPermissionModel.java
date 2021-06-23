package di.uoa.gr.dira.models.project.permission;

import di.uoa.gr.dira.models.IModel;
import di.uoa.gr.dira.shared.PermissionType;
import di.uoa.gr.dira.shared.PermissionTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class ProjectUserPermissionModel implements IModel<Long> {
    Long id;

    @NotNull
    @Positive
    Long customerId;

    @NotNull
    Set<PermissionTypeEnum> permissions;

    // This needs to be Integer and not int because ModelMapper throws NullPointerException
    // for some reason :(
    public void setPermissionsFromInteger(Integer permissions) {
        this.permissions = new HashSet<>();
        PermissionType permissionType = PermissionType.of(permissions);
        if (permissionType.hasAdminPermissions()) {
            this.permissions.add(PermissionTypeEnum.ADMIN);
        } else {
            if (permissionType.hasReadPermissions()) {
                this.permissions.add(PermissionTypeEnum.READ);
            }

            if (permissionType.hasWritePermissions()) {
                this.permissions.add(PermissionTypeEnum.WRITE);
            }

            if (permissionType.hasDeletePermissions()) {
                this.permissions.add(PermissionTypeEnum.DELETE);
            }
        }
    }
}
