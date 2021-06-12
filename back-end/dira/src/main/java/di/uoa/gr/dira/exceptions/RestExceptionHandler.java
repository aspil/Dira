package di.uoa.gr.dira.exceptions;

import di.uoa.gr.dira.exceptions.commonExceptions.ActionNotPermittedException;
import di.uoa.gr.dira.exceptions.customer.CustomerAlreadyExistsException;
import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import di.uoa.gr.dira.exceptions.issue.IssueNotFoundException;
import di.uoa.gr.dira.exceptions.project.ProjectAlreadyExistsException;
import di.uoa.gr.dira.exceptions.project.ProjectNotFoundException;
import di.uoa.gr.dira.exceptions.project.permission.PermissionNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler({
            CustomerNotFoundException.class,
            CustomerAlreadyExistsException.class,
            ProjectNotFoundException.class,
            ProjectAlreadyExistsException.class,
            ActionNotPermittedException.class,
            BadCredentialsException.class,
    })
    public final ResponseEntity<RestApiError> handleException(Exception ex, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        if (ex instanceof CustomerNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return handleCustomerNotFoundException((CustomerNotFoundException) ex, headers, status, request);
        } else if (ex instanceof CustomerAlreadyExistsException) {
            HttpStatus status = HttpStatus.CONFLICT;
            return handleCustomerAlreadyExistsException((CustomerAlreadyExistsException) ex, headers, status, request);
        } else if (ex instanceof ProjectNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return handleProjectNotFoundException((ProjectNotFoundException) ex, headers, status, request);
        } else if (ex instanceof IssueNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return handleIssueNotFoundException((IssueNotFoundException) ex, headers, status, request);
        } else if (ex instanceof PermissionNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return handlePermissionNotFoundException((PermissionNotFoundException) ex, headers, status, request);
        } else if (ex instanceof ActionNotPermittedException) {
            HttpStatus status = HttpStatus.FORBIDDEN;
            return handleActionNotPermittedException((ActionNotPermittedException) ex, headers, status, request);
        } else if (ex instanceof BadCredentialsException) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            return handleBadCredentialsException((BadCredentialsException) ex, headers, status, request);
        } else if (ex instanceof ProjectAlreadyExistsException) {
            HttpStatus status = HttpStatus.CONFLICT;
            return handleProjectAlreadyExistsException((ProjectAlreadyExistsException) ex, headers, status, request);
        }

        return null;
    }

    private ResponseEntity<RestApiError> handleProjectNotFoundException(
            ProjectNotFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return handleExceptionInternal(ex, new RestApiError(ex.getMessage()), headers, status, request);
    }

    private ResponseEntity<RestApiError> handleProjectAlreadyExistsException(
            ProjectAlreadyExistsException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return handleExceptionInternal(ex, new RestApiError(ex.getMessage()), headers, status, request);
    }

    private ResponseEntity<RestApiError> handleCustomerNotFoundException(
            CustomerNotFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return handleExceptionInternal(ex, new RestApiError(ex.getMessage()), headers, status, request);
    }

    private ResponseEntity<RestApiError> handleCustomerAlreadyExistsException(
            CustomerAlreadyExistsException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return handleExceptionInternal(ex, new RestApiError(ex.getMessage()), headers, status, request);
    }

    private ResponseEntity<RestApiError> handleIssueNotFoundException(
            IssueNotFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return handleExceptionInternal(ex, new RestApiError(ex.getMessage()), headers, status, request);
    }

    private ResponseEntity<RestApiError> handlePermissionNotFoundException(
            PermissionNotFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return handleExceptionInternal(ex, new RestApiError(ex.getMessage()), headers, status, request);
    }

    private ResponseEntity<RestApiError> handleActionNotPermittedException(
            ActionNotPermittedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return handleExceptionInternal(ex, new RestApiError(ex.getMessage()), headers, status, request);
    }

    private ResponseEntity<RestApiError> handleBadCredentialsException(
            BadCredentialsException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return handleExceptionInternal(ex, new RestApiError(ex.getMessage()), headers, status, request);
    }

    private ResponseEntity<RestApiError> handleExceptionInternal(
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
