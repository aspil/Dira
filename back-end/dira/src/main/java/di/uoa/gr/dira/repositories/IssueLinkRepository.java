package di.uoa.gr.dira.repositories;

import di.uoa.gr.dira.entities.issue.IssueLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueLinkRepository extends JpaRepository<IssueLink, Long> {
}
