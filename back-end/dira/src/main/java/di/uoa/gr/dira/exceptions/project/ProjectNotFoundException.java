package di.uoa.gr.dira.exceptions.project;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String attribute, String value) {
        super(String.format("Project with %s %s was not found", attribute, value));
    }
}
