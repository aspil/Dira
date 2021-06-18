package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.entities.customer.Customer;
import di.uoa.gr.dira.exceptions.commonExceptions.ActionNotPermittedException;
import di.uoa.gr.dira.exceptions.security.InvalidOldPasswordException;
import di.uoa.gr.dira.exceptions.security.PasswordResetTokenException;
import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.models.customer.PasswordModel;
import di.uoa.gr.dira.services.customerService.ICustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Validated
@RestController
public class ResetPasswordController {
    private final ICustomerService service;
    private final JavaMailSender mailSender;
    private final Environment env;

    public ResetPasswordController(ICustomerService service,
                                   JavaMailSender mailSender,
                                   Environment env) {
        this.service = service;
        this.mailSender = mailSender;
        this.env = env;
    }


    @PostMapping("resetPassword")
    public ResponseEntity<Void> resetPassword(HttpServletRequest request, @RequestParam("email") final String customerEmail) {
        final String token = UUID.randomUUID().toString();
        CustomerModel customerModel = service.createPasswordResetTokenForUser(customerEmail, token);
        mailSender.send(constructResetTokenEmail(getAppUrl(request), token, customerModel));
        return ResponseEntity.ok().build();
    }

    @PostMapping("savePassword")
    public ResponseEntity<Void> savePassword(@Valid PasswordModel passwordModel) {

        String result = service.validatePasswordResetToken(passwordModel.getToken());
        if(result != null) {
            throw new PasswordResetTokenException("PasswordResetToken", passwordModel.getToken());
        }

        service.changeUserPassword(passwordModel);

        return ResponseEntity.ok().build();
    }

    @PostMapping("updatePassword")
    public ResponseEntity<Void> changeUserPassword(@Valid PasswordModel passwordModel) {
        Optional<CustomerModel> customer = service.findByEmail(((Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
        if (customer.isPresent()) {
            if (!service.checkIfValidOldPassword(passwordModel.getOldPassword(), customer.get().getPassword())) {
                throw new InvalidOldPasswordException();
            }
            service.changeUserPassword(passwordModel);
        }
        else {
            // customer with the above email not found actually, could not get its info since customer is null
            throw new ActionNotPermittedException();
        }
        return ResponseEntity.ok().build();
    }

    private SimpleMailMessage constructEmail(String subject, String body, CustomerModel customerModel) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(customerModel.getEmail());
        email.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
        return email;
    }

    private String getAppUrl(HttpServletRequest request) {
        return String.format("http://%s:%d%s", request.getServerName(), request.getServerPort(), request.getContextPath());
    }

    private SimpleMailMessage constructResetTokenEmail(String contextPath, String token, CustomerModel customerModel) {
        String url = String.format("%s/user/changePassword?token=%s", contextPath, token);
        String message = String.format("Please reset your password by clicking the link below\n%s", url);
        return constructEmail("Reset Password", message, customerModel);
    }
}
