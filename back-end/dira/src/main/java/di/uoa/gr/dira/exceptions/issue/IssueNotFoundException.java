package di.uoa.gr.dira.exceptions.issue;

public class IssueNotFoundException extends RuntimeException {
    public IssueNotFoundException(String attribute, String value) {
        super(String.format("Issue with %s %s was not found", attribute, value));
    }
}
