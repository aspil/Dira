package di.uoa.gr.dira.repositories;

import di.uoa.gr.dira.entities.issue.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    Optional<Issue> findByKey(String key);
}
