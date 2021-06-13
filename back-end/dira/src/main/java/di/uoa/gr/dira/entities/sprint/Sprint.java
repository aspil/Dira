package di.uoa.gr.dira.entities.sprint;

import di.uoa.gr.dira.entities.issue.Issue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sprint_id")
    private Long id;

    @OneToMany(mappedBy = "belongsToSprint")
    private List<Issue> issues;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date dueDate;

}
