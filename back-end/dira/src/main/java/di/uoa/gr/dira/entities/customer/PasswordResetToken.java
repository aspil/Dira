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
public class PasswordResetToken {

    private static final int EXPIRATION = 60;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Customer.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "customer_id")
    private Customer customer;

    private Date expiryDate;

    public PasswordResetToken(String token, Customer customer) {
        this.token = token;
        this.customer = customer;
    }
}
