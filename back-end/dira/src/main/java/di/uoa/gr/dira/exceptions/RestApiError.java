package di.uoa.gr.dira.exceptions;

import lombok.*;

import java.util.Date;

@Data
public class RestApiError {
    private final RestApiErrorPayload error;

    public RestApiError(String message) {
        this.error = RestApiErrorPayload.builder()
                .message(message)
                .build();
    }

    @Data
    @Builder
    private static final class RestApiErrorPayload {
        private String message;
        @Builder.Default
        private Date timestamp = new Date();
    }
}
