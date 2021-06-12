package di.uoa.gr.dira.exceptions.project;

public class ProjectAlreadyExistsException extends RuntimeException {
    public ProjectAlreadyExistsException(String attribute, String value) {
        super(String.format("Project with %s %s already exists", attribute, value));
    }
}
