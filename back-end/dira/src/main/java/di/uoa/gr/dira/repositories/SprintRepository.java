package di.uoa.gr.dira.repositories;

import di.uoa.gr.dira.entities.sprint.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long>  {
}
