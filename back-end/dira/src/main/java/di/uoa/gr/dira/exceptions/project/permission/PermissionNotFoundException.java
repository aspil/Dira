package di.uoa.gr.dira.exceptions.project.permission;

public class PermissionNotFoundException extends RuntimeException {
    public PermissionNotFoundException(String msg) {
        super(msg);
    }

    public PermissionNotFoundException(String attribute, String value) {
        super(String.format("Permission with %s %s was not found", attribute, value));
    }
}
