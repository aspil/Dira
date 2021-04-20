package di.uoa.gr.dira.entities.issue;

import di.uoa.gr.dira.shared.IssueLinkTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_link_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "issue_id")
    private Issue linkedIssue;

    @Enumerated(EnumType.STRING)
    private IssueLinkTypeEnum linkType;
}
