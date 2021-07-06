package di.uoa.gr.dira.repositories;

import di.uoa.gr.dira.entities.customer.PasswordResetPin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetPinRepository extends JpaRepository<PasswordResetPin, Long> {
    Optional<PasswordResetPin> findByPin(String pin);
}