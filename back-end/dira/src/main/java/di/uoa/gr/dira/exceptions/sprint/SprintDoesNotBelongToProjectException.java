package di.uoa.gr.dira.exceptions.sprint;

public class SprintDoesNotBelongToProjectException extends RuntimeException {
    public SprintDoesNotBelongToProjectException(String attribute, String value) {
        super(String.format("Sprint with %s %s does not belong to the project", attribute, value));
    }
}
