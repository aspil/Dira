package di.uoa.gr.dira.entities.issue;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class IssueLabel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_label_id")
    private Long id;

    @Column(nullable = false)
    private String label;
}
