package di.uoa.gr.dira.exceptions;


import lombok.AllArgsConstructor;
import lombok.Data;

public class RestApiError {
    private final RestApiErrorPayload error;

    public RestApiError(String message) {
        this.error = new RestApiErrorPayload(message);
    }

    public String getMessage() {
        return this.error.getMessage();
    }

    @Data
    @AllArgsConstructor
    private static final class RestApiErrorPayload {
        private String message;
    }
}
