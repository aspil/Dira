package di.uoa.gr.dira.exceptions.security;

public class PasswordResetTokenException extends RuntimeException {
    public PasswordResetTokenException(String attribute, String value) {
        super(String.format("Could not validate %s with token: [%s]", attribute, value));
    }
}
