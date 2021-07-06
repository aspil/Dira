package di.uoa.gr.dira.exceptions.security;

public class PasswordResetPinException extends RuntimeException {
    public PasswordResetPinException(String msg) {
        super(msg);
    }

    public PasswordResetPinException(String attribute, String value) {
        super(String.format("Could not validate %s with token: [%s]", attribute, value));
    }
}
