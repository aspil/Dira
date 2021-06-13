package di.uoa.gr.dira.exceptions.security;

public class InvalidOldPasswordException extends RuntimeException {
    public InvalidOldPasswordException() {
        super("Invalid Old Password!");
    }
}
