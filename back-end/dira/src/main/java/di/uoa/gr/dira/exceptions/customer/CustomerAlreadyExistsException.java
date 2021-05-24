package di.uoa.gr.dira.exceptions.customer;

public class CustomerAlreadyExistsException extends RuntimeException {
    public CustomerAlreadyExistsException(String attribute, String value) {
        super(String.format("Customer with %s %s already exists", attribute, value));
    }
}