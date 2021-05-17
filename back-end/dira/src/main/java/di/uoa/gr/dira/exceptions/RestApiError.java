package di.uoa.gr.dira.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
public class RestApiError {
    private final RestApiErrorPayload error;
    private final Date timestamp;

    public RestApiError(String message) {
        this.error = new RestApiErrorPayload(message);
        this.timestamp = new Date();
    }

    @Data
    @AllArgsConstructor
    private static final class RestApiErrorPayload {
        private String message;
    }
}
