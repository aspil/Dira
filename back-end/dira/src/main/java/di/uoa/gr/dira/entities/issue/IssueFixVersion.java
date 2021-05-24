package di.uoa.gr.dira.entities.issue;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class IssueFixVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_fix_version_id")
    private Long id;

    @Column(nullable = false)
    private String fixVersion;
}
