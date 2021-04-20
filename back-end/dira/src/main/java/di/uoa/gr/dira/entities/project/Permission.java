package di.uoa.gr.dira.entities.project;

import di.uoa.gr.dira.entities.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer user;

    private int permissions;
}
