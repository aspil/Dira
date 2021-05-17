package di.uoa.gr.dira.exceptions.customer;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String attribute, String value) {
        super(String.format("Customer with %s '%s' was not found", attribute, value));
    }
}
