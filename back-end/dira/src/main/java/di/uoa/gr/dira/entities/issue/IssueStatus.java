package di.uoa.gr.dira.entities.issue;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class IssueStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_status_id")
    Long id;

    @Column(nullable = false, length = 20)
    String status;
}
