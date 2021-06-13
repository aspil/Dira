package di.uoa.gr.dira.exceptions.commonExceptions;

public class ActionNotPermittedException extends RuntimeException {
    public ActionNotPermittedException() {
        super("Action Not Permitted!");
    }

    public ActionNotPermittedException(String msg) {
        super(msg);
    }
}
