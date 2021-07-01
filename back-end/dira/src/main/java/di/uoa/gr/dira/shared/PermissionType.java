package di.uoa.gr.dira.shared;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class PermissionType {
    private static final int READ_PERMISSION = 1;
    private static final int WRITE_PERMISSION = 1 << 1;
    private static final int DELETE_PERMISSION = 1 << 2;
    private static final int ADMIN_PERMISSION = (READ_PERMISSION | WRITE_PERMISSION | DELETE_PERMISSION) | 1 << 3;

    public static final PermissionType READ = new PermissionType(READ_PERMISSION);
    public static final PermissionType WRITE = new PermissionType(WRITE_PERMISSION);
    public static final PermissionType DELETE = new PermissionType(DELETE_PERMISSION);
    public static final PermissionType ADMIN = new PermissionType(ADMIN_PERMISSION);

    private final int permission;

    public static PermissionType of(int permission) {
        return new PermissionType(permission);
    }

    public static PermissionType fromPermissionSet(Set<PermissionTypeEnum> permissions) {
        int permission = 0;
        for (PermissionTypeEnum perm: permissions) {
            permission |= PermissionType.fromPermissionTypeEnum(perm).getPermission();
        }
        return PermissionType.of(permission);
    }

    public static PermissionType fromPermissionTypeEnum(PermissionTypeEnum permissionTypeEnum) {
        switch (permissionTypeEnum) {
            case READ:
                return PermissionType.of(READ_PERMISSION);
            case WRITE:
                return PermissionType.of(WRITE_PERMISSION);
            case DELETE:
                return PermissionType.of(DELETE_PERMISSION);
            case ADMIN:
                return PermissionType.of(ADMIN_PERMISSION);
            default:
                return null; // should never happen
        }
    }

    public boolean hasPermission(int permission) {
        return (this.permission & permission) != 0;
    }

    public boolean hasAdminPermissions() {
        return this.permission == ADMIN_PERMISSION;
    }

    public boolean hasReadPermissions() {
        return hasPermission(READ_PERMISSION);
    }

    public boolean hasWritePermissions() {
        return hasPermission(WRITE_PERMISSION);
    }

    public boolean hasDeletePermissions() {
        return hasPermission(DELETE_PERMISSION);
    }
}