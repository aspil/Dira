package di.uoa.gr.dira.entities.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class PasswordResetPin {

    private static final int EXPIRATION_MS = 7_200_000;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String pin;

    @OneToOne
    @JoinColumn(nullable = false, name = "customer_id")
    private Customer customer;

    private Date expiryDate;

    public PasswordResetPin(String pin, Customer customer) {
        this.pin = pin;
        this.customer = customer;
        this.expiryDate = new Date(System.currentTimeMillis() + EXPIRATION_MS);
    }
}
