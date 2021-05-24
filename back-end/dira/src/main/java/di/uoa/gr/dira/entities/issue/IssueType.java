package di.uoa.gr.dira.entities.issue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_type_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String type;

    public static IssueType EPIC = new IssueType(1L, "Epic");
    public static IssueType STORY = new IssueType(2L, "Story");
    public static IssueType DEFECT = new IssueType(3L, "Defect");
}
