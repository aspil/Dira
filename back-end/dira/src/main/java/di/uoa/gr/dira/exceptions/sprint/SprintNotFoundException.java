package di.uoa.gr.dira.exceptions.sprint;

public class SprintNotFoundException extends RuntimeException {
    public SprintNotFoundException(String attribute, String value) {
        super(String.format("Sprint with %s %s does not exist", attribute, value));
    }
}
