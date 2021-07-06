package di.uoa.gr.dira.controllers;

import di.uoa.gr.dira.exceptions.security.PasswordResetPinException;
import di.uoa.gr.dira.models.customer.CustomerModel;
import di.uoa.gr.dira.models.customer.PasswordResetModel;
import di.uoa.gr.dira.services.customerService.ICustomerService;
import di.uoa.gr.dira.util.PinGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.core.env.Environment;

import javax.validation.Valid;
import java.util.Objects;

@Validated
@RestController
public class ResetPasswordController {
    private final ICustomerService customerService;
    private final JavaMailSender mailSender;
    private final Environment env;

    public ResetPasswordController(ICustomerService customerService,
                                   JavaMailSender mailSender,
                                   Environment env) {
        this.customerService = customerService;
        this.mailSender = mailSender;
        this.env = env;
    }

    @PostMapping("resetPassword")
    public ResponseEntity<Void> resetPassword(@RequestParam("email") String customerEmail) {
        String pin = String.format("%05d", PinGenerator.generateRandomPin());
        CustomerModel customerModel = customerService.createResetPinForCustomer(customerEmail, pin);
        mailSender.send(constructResetPasswordEmail(pin, customerModel));
        return ResponseEntity.ok().build();
    }

    @PostMapping("finalizeResetPassword")
    public ResponseEntity<Void> finalizeResetPassword(@Valid @RequestBody PasswordResetModel passwordResetModel) {
        String result = customerService.validatePasswordResetPin(passwordResetModel.getPin());

        if(!result.isEmpty()) {
            throw new PasswordResetPinException("PasswordResetPin", passwordResetModel.getPin());
        }

        CustomerModel customer = customerService.getCustomerByResetPin(passwordResetModel.getPin())
                .orElseThrow(() -> new PasswordResetPinException(
                        String.format("Could not find customer with token [%s]", passwordResetModel.getPin()))
                );

        customer.setPassword(passwordResetModel.getNewPassword());
        customerService.save(customer);

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

    private SimpleMailMessage constructResetPasswordEmail(String pin, CustomerModel customerModel) {
        String message = String.format("Please reset your password by providing the code below\nCode: %s", pin);
        return constructEmail("Reset Dira Password", message, customerModel);
    }
}
