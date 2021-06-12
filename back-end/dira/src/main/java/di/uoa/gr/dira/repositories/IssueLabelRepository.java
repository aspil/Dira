package di.uoa.gr.dira.repositories;

import di.uoa.gr.dira.entities.issue.IssueLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssueLabelRepository extends JpaRepository<IssueLabel, Long> {
    Optional<IssueLabel> findByName(String name);
}
