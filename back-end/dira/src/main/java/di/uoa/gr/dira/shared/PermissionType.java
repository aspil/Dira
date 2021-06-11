package di.uoa.gr.dira.shared;


public class PermissionType {
    public static final int READ = 1;
    public static final int WRITE = 1 << 1;
    public static final int DELETE = 1 << 2;
    public static final int ADMIN = READ | WRITE | DELETE;

    public static boolean hasAdminPermissions(int permission) {
        return ADMIN == (permission & ADMIN);
    }
    public static boolean hasReadPermissions(int permission) {
        return READ == (permission & READ);
    }
    public static boolean hasWritePermissions(int permission) {
        return WRITE == (permission & WRITE);
    }
    public static boolean hasDeletePermissions(int permission) {
        return DELETE == (permission & DELETE);
    }
}
