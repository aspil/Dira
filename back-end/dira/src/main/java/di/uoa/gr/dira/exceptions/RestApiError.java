package di.uoa.gr.dira.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class RestApiError {
    private final RestApiErrorPayload error;

    public RestApiError(String message) {
        this.error = new RestApiErrorPayload(message);
    }

    @Data
    @AllArgsConstructor
    private static final class RestApiErrorPayload {
        private String message;
    }
}
