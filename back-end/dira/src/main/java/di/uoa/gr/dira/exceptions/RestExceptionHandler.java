package di.uoa.gr.dira.exceptions;

import di.uoa.gr.dira.exceptions.commonExceptions.ActionNotPermittedException;
import di.uoa.gr.dira.exceptions.commonExceptions.CustomMessageException;
import di.uoa.gr.dira.exceptions.security.InvalidOldPasswordException;
import di.uoa.gr.dira.exceptions.security.PasswordResetPinException;
import di.uoa.gr.dira.exceptions.customer.CustomerAlreadyExistsException;
import di.uoa.gr.dira.exceptions.customer.CustomerNotFoundException;
import di.uoa.gr.dira.exceptions.issue.IssueNotFoundException;
import di.uoa.gr.dira.exceptions.project.ProjectAlreadyExistsException;
import di.uoa.gr.dira.exceptions.project.ProjectNotFoundException;
import di.uoa.gr.dira.exceptions.project.permission.PermissionNotFoundException;
import io.jsonwebtoken.JwtException;
import di.uoa.gr.dira.exceptions.sprint.SprintDoesNotBelongToProjectException;
import di.uoa.gr.dira.exceptions.sprint.SprintNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler({
            // Customer
            CustomerNotFoundException.class,
            CustomerAlreadyExistsException.class,
            // Project
            ProjectNotFoundException.class,
            ProjectAlreadyExistsException.class,
            // Project Permissions
            PermissionNotFoundException.class,
            // Issue
            IssueNotFoundException.class,
            SprintNotFoundException.class,
            // Sprint
            SprintDoesNotBelongToProjectException.class,
            // General
            CustomMessageException.class,
            ActionNotPermittedException.class,
            PasswordResetPinException.class,
            InvalidOldPasswordException.class,
            BadCredentialsException.class,
            JwtException.class,
            MethodArgumentNotValidException.class,
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
        else if (ex instanceof PasswordResetPinException) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            return handlePasswordResetTokenException((PasswordResetPinException) ex, headers, status, request);
        }
        else if (ex instanceof InvalidOldPasswordException) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            return handleInvalidOldPasswordException((InvalidOldPasswordException) ex, headers, status, request);
        }
        else if (ex instanceof JwtException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleJwtException((JwtException) ex, headers, status, request);
        }
        else if (ex instanceof CustomMessageException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return handleCustomMessageException((CustomMessageException) ex, headers, status, request);
        }
        else if (ex instanceof SprintDoesNotBelongToProjectException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return handleSprintDoesNotBelongToProject((SprintDoesNotBelongToProjectException) ex, headers, status, request);
        }
        else if (ex instanceof SprintNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return handleSprintNotFoundException((SprintNotFoundException) ex, headers, status, request);
        } else if (ex instanceof  MethodArgumentNotValidException) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return handleMethodArgumentNotValidException((MethodArgumentNotValidException) ex, headers, status, request);
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

    private ResponseEntity<RestApiError> handleSprintDoesNotBelongToProject(
            SprintDoesNotBelongToProjectException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return handleExceptionInternal(ex, new RestApiError(ex.getMessage()), headers, status, request);
    }

    private ResponseEntity<RestApiError> handleSprintNotFoundException(
            SprintNotFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return handleExceptionInternal(ex, new RestApiError(ex.getMessage()), headers, status, request);
    }

    private ResponseEntity<RestApiError> handleCustomMessageException(
            CustomMessageException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return handleExceptionInternal(ex, new RestApiError(ex.getMessage()), headers, status, request);
    }

    private ResponseEntity<RestApiError> handlePasswordResetTokenException(
            PasswordResetPinException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return handleExceptionInternal(ex, new RestApiError(ex.getMessage()), headers, status, request);
    }

    private ResponseEntity<RestApiError> handleInvalidOldPasswordException(
            InvalidOldPasswordException ex,
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

    private ResponseEntity<RestApiError> handleJwtException(
            JwtException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return handleExceptionInternal(ex, new RestApiError(ex.getMessage()), headers, status, request);
    }

    private ResponseEntity<RestApiError> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }

        String msg = String.join("|", details);
        return handleExceptionInternal(ex ,new RestApiError(msg), headers, status, request);
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
