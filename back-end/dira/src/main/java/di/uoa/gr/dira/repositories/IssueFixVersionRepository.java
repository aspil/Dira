package di.uoa.gr.dira.repositories;

import di.uoa.gr.dira.entities.issue.IssueFixVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssueFixVersionRepository extends JpaRepository<IssueFixVersion, Long> {
    Optional<IssueFixVersion> findByFixVersion(String fixVersion);
}
