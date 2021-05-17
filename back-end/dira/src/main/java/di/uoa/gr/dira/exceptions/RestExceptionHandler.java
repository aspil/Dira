package di.uoa.gr.dira.exceptions;

import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler({
            CustomerNotFoundException.class
    })
    public final ResponseEntity<RestApiError> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        if (ex instanceof CustomerNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return handleCustomerNotFoundException((CustomerNotFoundException) ex, headers, status, request);
        }

        return null;
    }

    protected ResponseEntity<RestApiError> handleCustomerNotFoundException(
            CustomerNotFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return handleExceptionInternal(ex, new RestApiError(ex.getMessage()), headers, status, request);
    }

    protected ResponseEntity<RestApiError> handleExceptionInternal(
            Exception ex,
            RestApiError body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }
}
