package di.uoa.gr.dira.security;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.*;


import java.util.Arrays;
import java.util.List;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword _arg) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                // At least 8 characters up to 30
                new LengthRule(8, 30),
                // At least 1 upper case letter
                new UppercaseCharacterRule(1),
                // At least 1 lower case letter
                new LowercaseCharacterRule(1),
                // At least 1 digit
                new DigitCharacterRule(1),
                // At least 1 symbol (special character)
                new SpecialCharacterRule(1),
                // No whitespace
                new WhitespaceRule()));

        RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        List<String> messages = validator.getMessages(result);

        String messageTemplate = String.join("|", messages);

        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }

}