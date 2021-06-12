package di.uoa.gr.dira.shared;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PermissionType {
    private static final int READ_PERMISSION = 1;
    private static final int WRITE_PERMISSION = 1 << 1;
    private static final int DELETE_PERMISSION = 1 << 2;
    private static final int ADMIN_PERMISSION = READ_PERMISSION | WRITE_PERMISSION | DELETE_PERMISSION;

    public static final PermissionType READ = new PermissionType(READ_PERMISSION);
    public static final PermissionType WRITE = new PermissionType(WRITE_PERMISSION);
    public static final PermissionType DELETE = new PermissionType(DELETE_PERMISSION);
    public static final PermissionType ADMIN = new PermissionType(ADMIN_PERMISSION);

    private final int permission;

    public boolean hasPermission(int permission) {
        return (this.permission & permission) != 0;
    }

    public boolean hasAdminPermissions() {
        return hasPermission(ADMIN_PERMISSION);
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